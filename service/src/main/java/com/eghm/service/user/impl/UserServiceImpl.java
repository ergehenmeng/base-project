package com.eghm.service.user.impl;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.EmailType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.SmsType;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.AesUtil;
import com.eghm.common.utils.RegExpUtil;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.user.UserMapper;
import com.eghm.dao.model.business.LoginLog;
import com.eghm.dao.model.user.User;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.model.dto.login.AccountLoginRequest;
import com.eghm.model.dto.login.SmsLoginRequest;
import com.eghm.model.dto.register.RegisterUserRequest;
import com.eghm.model.dto.user.BindEmailRequest;
import com.eghm.model.dto.user.ChangeEmailRequest;
import com.eghm.model.dto.user.SendEmailAuthCodeRequest;
import com.eghm.model.dto.user.UserAuthRequest;
import com.eghm.model.ext.*;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.queue.TaskHandler;
import com.eghm.queue.task.LoginLogTask;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.EmailService;
import com.eghm.service.common.SmsService;
import com.eghm.service.common.TokenService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.LoginLogService;
import com.eghm.service.user.UserService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
@Service("userService")
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j
public class UserServiceImpl implements UserService {

    private SysConfigApi sysConfigApi;

    private UserMapper userMapper;

    private Encoder encoder;

    private TokenService tokenService;

    private SmsService smsService;

    private LoginLogService loginLogService;

    private TaskHandler taskHandler;

    private EmailService emailService;

    private CacheService cacheService;

    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    @Autowired
    public void setLoginLogService(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @Autowired
    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    @Override
    public User doRegister(UserRegister register) {
        User user = DataUtil.copy(register, User.class);
        this.encodePassword(user);
        this.generateNickName(user);
        userMapper.insertSelective(user);
        this.doPostRegister(user);
        return user;
    }

    /**
     * 用户注册后置处理
     *
     * @param user 用户信息
     */
    private void doPostRegister(User user) {
        // 可添加其他处理
    }

    /**
     * 对登陆密码进行加密
     *
     * @param user 用户信息
     */
    private void encodePassword(User user) {
        if (StrUtil.isNotBlank(user.getPwd())) {
            user.setPwd(encoder.encode(user.getPwd()));
        }
    }

    /**
     * 昵称为空时,动态生成昵称
     *
     * @param user 用户信息
     */
    private void generateNickName(User user) {
        if (StrUtil.isBlank(user.getNickName())) {
            user.setNickName(sysConfigApi.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
    }


    @Override
    public LoginTokenVO accountLogin(AccountLoginRequest login) {
        User user = this.getByAccount(login.getAccount());
        if (user == null || !encoder.matches(login.getPwd(), user.getPwd())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        RequestMessage request = ApiHolder.get();
        LoginLog loginLog = loginLogService.getBySerialNumber(user.getId(), request.getSerialNumber());
        if (loginLog == null && StrUtil.isNotBlank(user.getMobile())) {
            // 新设备登陆时,如果使用密码登陆需要验证短信
            BusinessException exception = new BusinessException(ErrorCode.NEW_DEVICE_LOGIN);
            exception.setData(user.getMobile());
            throw exception;
        }
        return this.doLogin(user, login.getIp());
    }


    @Override
    public LoginTokenVO smsLogin(SmsLoginRequest login) {
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
        Token token = tokenService.createToken(user.getId(), request.getChannel());
        // 记录登陆日志信息
        LoginRecord record = LoginRecord.builder()
                .ip(IpUtil.ipToLong(ip))
                .userId(user.getId())
                .channel(request.getChannel())
                .deviceBrand(request.getDeviceBrand())
                .deviceModel(request.getDeviceModel())
                .softwareVersion(request.getVersion())
                .serialNumber(request.getSerialNumber())
                .build();
        taskHandler.executeLoginLog(new LoginLogTask(record, loginLogService));
        return LoginTokenVO.builder().token(token.getToken()).refreshToken(token.getRefreshToken()).build();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public User getByAccount(String account) {
        if (RegExpUtil.mobile(account)) {
            return userMapper.getByMobile(account);
        }
        return userMapper.getByEmail(account);
    }


    @Override
    public void updateState(Integer userId, Boolean state) {
        User user = new User();
        user.setId(userId);
        user.setState(state);
        userMapper.updateByPrimaryKeySelective(user);
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
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
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
    public LoginTokenVO registerByMobile(RegisterUserRequest request) {
        this.registerRedoVerify(request.getMobile());
        smsService.verifySmsCode(SmsType.REGISTER, request.getMobile(), request.getSmsCode());
        UserRegister register = DataUtil.copy(request, UserRegister.class);
        User user = this.doRegister(register);
        return this.doLogin(user, register.getRegisterIp());
    }


    @Override
    public void offline(int userId){
        Token token = tokenService.getByUserId(userId);
        if (token == null) {
            return;
        }
        long expire = tokenService.getTokenExpire(userId);
        if (expire > 0) {
            // 缓存踢下线的信息
            tokenService.cacheOfflineToken(token, expire);
        }
        tokenService.cleanRefreshToken(token.getRefreshToken());
        tokenService.cleanToken(token.getToken());
        tokenService.cleanUserId(userId);
    }

    @Override
    public void sendBindEmail(String email, Integer userId) {
        this.checkEmail(email);
        SendEmail sendEmail = new SendEmail();
        sendEmail.setType(EmailType.BIND_EMAIL);
        sendEmail.put("userId", userId);
        emailService.sendEmail(sendEmail);
    }

    @Override
    public void bindEmail(BindEmailRequest request) {
        User user = userMapper.selectByPrimaryKey(request.getUserId());
        if (StrUtil.isNotBlank(user.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.BIND_EMAIL);
        emailService.verifyEmailCode(emailCode);
        user.setEmail(request.getEmail());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void realNameAuth(UserAuthRequest request) {
        User user = new User();
        user.setId(request.getUserId());
        user.setRealName(request.getRealName());
        user.setBirthday(IdcardUtil.getBirthByIdCard(request.getIdCard()));
        user.setIdCard(AesUtil.encrypt(request.getIdCard(), applicationProperties.getSecretKey()));
        user.setSex((byte)IdcardUtil.getGenderByIdCard(request.getIdCard()));
        userMapper.updateByPrimaryKeySelective(user);
        //TODO 实名制认证
    }

    /**
     * 查看邮箱是会否被占用
     * @param email 邮箱号
     */
    @Override
    public void checkEmail(String email) {
        User user = userMapper.getByEmail(email);
        if (user == null) {
            log.warn("邮箱号已被占用 email:[{}]", email);
            throw new BusinessException(ErrorCode.EMAIL_OCCUPY_ERROR);
        }
    }

    @Override
    public void sendChangeEmailSms(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (StrUtil.isBlank(user.getMobile())) {
            log.warn("未绑定手机号,无法发送邮箱验证短信 userId:[{}]", userId);
            throw new BusinessException(ErrorCode.MOBILE_NOT_BIND);
        }
        smsService.sendSmsCode(SmsType.CHANGE_EMAIL, user.getMobile());
    }

    @Override
    public void sendChangeEmailCode(SendEmailAuthCodeRequest request) {
        User user = userMapper.selectByPrimaryKey(request.getUserId());
        smsService.verifySmsCode(SmsType.CHANGE_EMAIL, user.getMobile(), request.getSmsCode());
        this.checkEmail(request.getEmail());
        SendEmail email = new SendEmail();
        email.setType(EmailType.BIND_EMAIL);
        email.put("userId", request.getUserId());
        emailService.sendEmail(email);
    }

    @Override
    public void changeEmail(ChangeEmailRequest request) {
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.CHANGE_EMAIL);
        emailService.verifyEmailCode(emailCode);
        User user = userMapper.selectByPrimaryKey(request.getUserId());
        user.setEmail(user.getEmail());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 注册手机号被占用校验
     *
     * @param mobile 手机号
     */
    private void registerRedoVerify(String mobile) {
        User user = userMapper.getByMobile(mobile);
        if (user == null) {
            throw new BusinessException(ErrorCode.MOBILE_REGISTER_REDO);
        }
    }

}
