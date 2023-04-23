package com.eghm.service.user.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.email.SendEmail;
import com.eghm.dto.ext.*;
import com.eghm.dto.login.AccountLoginDTO;
import com.eghm.dto.login.SmsLoginDTO;
import com.eghm.dto.register.RegisterUserDTO;
import com.eghm.dto.user.BindEmailDTO;
import com.eghm.dto.user.ChangeEmailDTO;
import com.eghm.dto.user.SendEmailAuthCodeDTO;
import com.eghm.dto.user.UserAuthDTO;
import com.eghm.enums.*;
import com.eghm.exception.BusinessException;
import com.eghm.exception.DataException;
import com.eghm.handler.chain.HandlerChain;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.mapper.UserMapper;
import com.eghm.model.LoginDevice;
import com.eghm.model.User;
import com.eghm.model.UserScoreLog;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.EmailService;
import com.eghm.service.common.SmsService;
import com.eghm.service.common.TokenService;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.LoginService;
import com.eghm.service.user.UserScoreLogService;
import com.eghm.service.user.UserService;
import com.eghm.service.wechat.WeChatMpService;
import com.eghm.utils.*;
import com.eghm.vo.login.LoginTokenVO;
import com.eghm.vo.user.SignInVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
@Service("userService")
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysConfigApi sysConfigApi;

    private final UserMapper userMapper;

    private final Encoder encoder;

    private final TokenService tokenService;

    private final SmsService smsService;

    private final LoginService loginService;

    private final EmailService emailService;

    private final SystemProperties systemProperties;

    private final CacheService cacheService;

    private final UserScoreLogService userScoreLogService;

    private final HandlerChain handlerChain;

    private final WeChatMpService weChatMpService;

    private final MessageService rabbitMessageService;

    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User doRegister(UserRegister register) {
        User user = DataUtil.copy(register, User.class);
        user.setChannel(ApiHolder.getChannel());
        this.initUser(user);
        userMapper.insert(user);
        this.doPostRegister(user, register);
        return user;
    }

    /**
     * 用户注册后置处理
     *
     * @param user 用户信息
     */
    private void doPostRegister(User user, UserRegister register) {
        // 获取到用户id,再次更新邀请码
        String inviteCode = StringUtil.encryptNumber(user.getId());
        user.setInviteCode(inviteCode);
        userMapper.updateById(user);
        // 执行注册后置链
        MessageData data = new MessageData();
        data.setUser(user);
        data.setUserRegister(register);
        handlerChain.execute(data, HandlerEnum.REGISTER);
    }

    /**
     * 对用户部分信息进行初始化
     *
     * @param user 用户信息
     */
    private void initUser(User user) {
        if (StrUtil.isBlank(user.getNickName())) {
            user.setNickName(sysConfigApi.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
    }


    @Override
    public LoginTokenVO accountLogin(AccountLoginDTO login) {
        User user = this.getByAccount(login.getAccount());
        if (user == null || !encoder.match(login.getPwd(), user.getPwd())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        RequestMessage request = ApiHolder.get();
        LoginDevice loginLog = loginService.getBySerialNumber(user.getId(), request.getSerialNumber());
        if (loginLog == null && StrUtil.isNotBlank(user.getMobile())) {
            // 新设备登陆时,如果使用密码登陆需要验证短信,当然前提是用户已经绑定手机号码
            throw new DataException(ErrorCode.NEW_DEVICE_LOGIN, user.getMobile());
        }
        return this.doLogin(user, login.getIp());
    }


    @Override
    public LoginTokenVO smsLogin(SmsLoginDTO login) {
        User user = this.getByAccountRequired(login.getMobile());
        smsService.verifySmsCode(SmsType.LOGIN, login.getMobile(), login.getSmsCode());
        return this.doLogin(user, login.getIp());
    }

    /**
     * 移动端登陆系统
     * 1.清除旧token信息
     * 2.创建token
     * 3.添加登陆日志
     *
     * @param user 用户id
     * @param ip   登陆ip
     * @return token信息
     */
    private LoginTokenVO doLogin(User user, String ip) {
        // 将原用户踢掉
        this.offline(user.getId());
        RequestMessage request = ApiHolder.get();
        // 创建token
        RedisToken redisToken = tokenService.createToken(user.getId(), request.getChannel());
        // 记录登陆日志信息
        LoginRecord loginRecord = LoginRecord.builder()
                .ip(NetUtil.ipv4ToLong(ip))
                .userId(user.getId())
                .channel(request.getChannel())
                .deviceBrand(request.getDeviceBrand())
                .deviceModel(request.getDeviceModel())
                .softwareVersion(request.getVersion())
                .serialNumber(request.getSerialNumber())
                .build();
        rabbitMessageService.send(ExchangeQueue.LOGIN_LOG, loginRecord);
        return LoginTokenVO.builder().token(redisToken.getToken()).refreshToken(redisToken.getRefreshToken()).build();
    }


    @Override
    public User getByAccount(String account) {
        if (RegExpUtil.mobile(account)) {
            return this.getByMobile(account);
        }
        return this.getByEmail(account);
    }


    @Override
    public void updateState(Long userId, Boolean state) {
        User user = new User();
        user.setId(userId);
        user.setState(state);
        userMapper.updateById(user);
        if (Boolean.FALSE.equals(user.getState())){
            this.offline(user.getId());
        }
    }

    @Override
    public void sendLoginSms(String mobile) {
        User user = this.getByAccountRequired(mobile);
        smsService.sendSmsCode(SmsType.LOGIN, user.getMobile());
    }

    @Override
    public void sendForgetSms(String mobile) {
        User user = this.getByAccountRequired(mobile);
        smsService.sendSmsCode(SmsType.FORGET, user.getMobile());
    }

    @Override
    public User getByAccountRequired(String account) {
        User user = this.getByAccount(account);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public void registerSendSms(String mobile) {
        this.registerRedoVerify(mobile);
        smsService.sendSmsCode(SmsType.REGISTER, mobile);
    }

    @Override
    public LoginTokenVO registerByMobile(RegisterUserDTO request) {
        this.registerRedoVerify(request.getMobile());
        smsService.verifySmsCode(SmsType.REGISTER, request.getMobile(), request.getSmsCode());
        UserRegister register = DataUtil.copy(request, UserRegister.class);
        User user = this.doRegister(register);
        return this.doLogin(user, register.getRegisterIp());
    }


    @Override
    public void offline(Long userId){
        RedisToken redisToken = tokenService.getByUserId(userId);
        if (redisToken == null) {
            return;
        }
        long expire = tokenService.getTokenExpire(userId);
        if (expire > 0) {
            // 缓存踢下线的信息
            tokenService.cacheOfflineToken(redisToken, expire);
        }
        tokenService.cleanRefreshToken(redisToken.getRefreshToken());
        tokenService.cleanToken(redisToken.getToken());
        tokenService.cleanUserId(userId);
    }

    @Override
    public void sendBindEmail(String email, Long userId) {
        this.checkEmail(email);
        SendEmail sendEmail = new SendEmail();
        sendEmail.setType(EmailType.BIND_EMAIL);
        sendEmail.put("userId", userId);
        emailService.sendEmail(sendEmail);
    }

    @Override
    public void bindEmail(BindEmailDTO request) {
        User user = userMapper.selectById(request.getUserId());
        if (StrUtil.isNotBlank(user.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.BIND_EMAIL);
        emailService.verifyEmailCode(emailCode);
        user.setEmail(request.getEmail());
        userMapper.updateById(user);
    }

    @Override
    public void realNameAuth(UserAuthDTO request) {
        User user = new User();
        user.setId(request.getUserId());
        user.setRealName(request.getRealName());
        user.setBirthday(IdcardUtil.getBirthByIdCard(request.getIdCard()));
        user.setIdCard(SecureUtil.aes(systemProperties.getApi().getSecretKey().getBytes(StandardCharsets.UTF_8)).encryptHex(request.getIdCard()));
        user.setSex(IdcardUtil.getGenderByIdCard(request.getIdCard()));
        userMapper.updateById(user);
    }

    /**
     * 查看邮箱是会否被占用
     * @param email 邮箱号
     */
    @Override
    public void checkEmail(String email) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getEmail, email);
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("邮箱号已被占用 email:[{}]", email);
            throw new BusinessException(ErrorCode.EMAIL_OCCUPY_ERROR);
        }
    }

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getEmail, email);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getByMobile(String mobile) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getMobile, mobile);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void sendChangeEmailSms(Long userId) {
        User user = userMapper.selectById(userId);
        if (StrUtil.isBlank(user.getMobile())) {
            log.warn("未绑定手机号,无法发送邮箱验证短信 userId:[{}]", userId);
            throw new BusinessException(ErrorCode.MOBILE_NOT_BIND);
        }
        smsService.sendSmsCode(SmsType.CHANGE_EMAIL, user.getMobile());
    }

    @Override
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        User user = userMapper.selectById(request.getUserId());
        smsService.verifySmsCode(SmsType.CHANGE_EMAIL, user.getMobile(), request.getSmsCode());
        this.checkEmail(request.getEmail());
        SendEmail email = new SendEmail();
        email.setType(EmailType.BIND_EMAIL);
        email.put("userId", request.getUserId());
        emailService.sendEmail(email);
    }

    @Override
    public void changeEmail(ChangeEmailDTO request) {
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.CHANGE_EMAIL);
        emailService.verifyEmailCode(emailCode);
        User user = userMapper.selectById(request.getUserId());
        user.setEmail(user.getEmail());
        userMapper.updateById(user);
    }

    @Override
    public void signIn(Long userId) {
        User user = userMapper.selectById(userId);
        long day = ChronoUnit.DAYS.between(user.getCreateTime(), LocalDateTime.now());
        String signKey = CacheConstant.USER_SIGN_IN + userId;
        Boolean signIn = cacheService.getBitmap(signKey, day);
        if (Boolean.TRUE.equals(signIn)) {
            log.warn("用户重复签到 userId:[{}]", userId);
            return;
        }
        // 今日签到记录到缓存中
        cacheService.setBitmap(signKey, day, true);
        int score = userScoreLogService.getSignInScore();
        UserScoreLog log = new UserScoreLog();
        log.setScore(score);
        log.setUserId(userId);
        log.setType(ScoreType.SIGN_IN.getValue());
        userScoreLogService.insert(log);
        user.setScore(user.getScore() + score);
        userMapper.updateById(user);
    }

    @Override
    public Boolean isSignInToday(User user) {
        long day = ChronoUnit.DAYS.between(user.getCreateTime(), LocalDateTime.now());
        return cacheService.getBitmap(CacheConstant.USER_SIGN_IN + user.getId(), day);
    }

    @Override
    public SignInVO getSignIn(Long userId) {
        User user = userMapper.selectById(userId);
        Date now = DateUtil.getNow();
        long day = ChronoUnit.DAYS.between(user.getCreateTime(), LocalDateTime.now());
        String signKey = CacheConstant.USER_SIGN_IN + userId;
        // 今日是否签到
        boolean todaySignIn = cacheService.getBitmap(signKey, day);
        List<Boolean> thisMonth = Lists.newArrayListWithCapacity(31);
        thisMonth.add(todaySignIn);
        SignInVO sign = new SignInVO();
        sign.setToday(todaySignIn);
        // 先将参数放进去,防止返回时整个属性字段都为空
        sign.setThisMonth(thisMonth);
        // 累计签到次数
        Long count = cacheService.getBitmapCount(signKey);
        sign.setAddUp(count == null ? 0 : count.intValue());
        Long bitmap64 = cacheService.getBitmap64(signKey, day);
        if (bitmap64 == null) {
            log.info("该用户尚未签到过 userId:[{}]", userId);
            return sign;
        }
        Date monthStart = DateUtil.firstDayOfMonth(now);
        // 本月已过天数
        long monthDays = DateUtil.diffDay(now, monthStart);
        for (int i = 0; i < monthDays; i++) {
            thisMonth.add(bitmap64 >> 1 << 1 != bitmap64);
            bitmap64 >>= 1;
        }
        Collections.reverse(thisMonth);
        return sign;
    }

    @Override
    public User getByInviteCode(String inviteCode) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getInviteCode, inviteCode);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public LoginTokenVO mpLogin(String jsCode, String ip) {
        WxOAuth2UserInfo userInfo = weChatMpService.auth2(jsCode);
        User user = this.getByOpenId(userInfo.getOpenid());
        // 注销的用户算新增
        if (user == null || Boolean.FALSE.equals(user.getState())) {
            user = this.doMpRegister(userInfo, ip);
        }
        return this.doLogin(user, ip);
    }

    @Override
    public User getByOpenId(String openId) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getOpenId, openId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void setPassword(String requestId, String password) {
        String value = cacheService.getValue(CacheConstant.VERIFY_MOBILE_PREFIX + requestId);
        if (value == null) {
            log.error("短信验证码认证已过期 [{}]", requestId);
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        User user = this.getByMobile(value);
        if (user == null) {
            log.error("验证码手机号不存在 [{}] [{}]", requestId, value);
            throw new BusinessException(ErrorCode.MOBILE_NOT_REGISTER);
        }
        user.setPwd(encoder.encode(password));
        userMapper.updateById(user);
    }

    /**
     * 微信公众号用户注册
     * @param info 微信用户信息
     * @param ip ip
     * @return 用户信息
     */
    private User doMpRegister(WxOAuth2UserInfo info, String ip) {
        User user = new User();
        user.setNickName(info.getNickname());
        user.setOpenId(info.getOpenid());
        user.setSex(info.getSex());
        user.setAvatar(info.getHeadImgUrl());
        user.setRegisterIp(IpUtil.ipToLong(ip));
        user.setChannel(Channel.WECHAT.name());
        this.initUser(user);
        userMapper.insert(user);
        UserRegister register = new UserRegister();
        register.setRegisterIp(ip);
        register.setNickName(info.getNickname());
        this.doPostRegister(user, register);
        return user;
    }

    /**
     * 注册手机号被占用校验
     *
     * @param mobile 手机号
     */
    private void registerRedoVerify(String mobile) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getMobile, mobile);
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("手机号被占用,无法注册用户 [{}]", mobile);
            throw new BusinessException(ErrorCode.MOBILE_REGISTER_REDO);
        }
    }
}
