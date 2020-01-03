package com.fanyin.service.user.impl;

import com.fanyin.common.constant.SmsTypeConstant;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.RegExpUtil;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.configuration.security.Encoder;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.dao.mapper.user.UserMapper;
import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.dto.register.RegisterUserRequest;
import com.fanyin.model.ext.*;
import com.fanyin.model.vo.login.LoginTokenVO;
import com.fanyin.queue.TaskHandler;
import com.fanyin.queue.task.LoginLogTask;
import com.fanyin.service.cache.CacheProxyService;
import com.fanyin.service.common.SmsService;
import com.fanyin.service.system.impl.SystemConfigApi;
import com.fanyin.service.user.LoginLogService;
import com.fanyin.service.user.UserExtService;
import com.fanyin.service.user.UserService;
import com.fanyin.utils.DataUtil;
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

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Encoder encoder;

    @Autowired
    private CacheProxyService cacheProxyService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private TaskHandler taskHandler;

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
     * 用户注册后置处理 初始化用户附加信息
     *
     * @param user 用户信息
     */
    private void doPostRegister(User user) {
        userExtService.init(user);
    }

    /**
     * 对登陆密码进行加密
     *
     * @param user 用户信息
     */
    private void encodePassword(User user) {
        if (StringUtil.isNotBlank(user.getPwd())) {
            user.setPwd(encoder.encode(user.getPwd()));
        }
    }

    /**
     * 昵称为空时,动态生成昵称
     *
     * @param user 用户信息
     */
    private void generateNickName(User user) {
        if (StringUtil.isBlank(user.getNickName())) {
            user.setNickName(systemConfigApi.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
    }


    @Override
    public LoginTokenVO accountLogin(AccountLoginRequest login) {
        User user = this.getByAccount(login.getAccount());
        if (user == null || !encoder.matches(login.getPwd(), user.getPwd())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        return this.doLogin(user, login.getIp());
    }


    @Override
    public LoginTokenVO smsLogin(SmsLoginRequest login) {
        User user = this.getByAccountRequired(login.getMobile());
        smsService.verifySmsCode(SmsTypeConstant.LOGIN_SMS, login.getMobile(), login.getSmsCode());
        return this.doLogin(user, login.getIp());
    }

    /**
     * 移动端登陆系统
     * 1.创建token
     * 2.添加登陆日志
     *
     * @param user 用户id
     * @param ip   登陆ip
     * @return token信息
     */
    private LoginTokenVO doLogin(User user, String ip) {
        RequestMessage request = RequestThreadLocal.get();
        AccessToken accessToken = cacheProxyService.createAccessToken(user, request.getChannel());
        LoginRecord record = LoginRecord.builder()
                .channel(request.getChannel())
                .ip(ip)
                .deviceBrand(request.getDeviceBrand())
                .deviceModel(request.getDeviceModel())
                .userId(user.getId())
                .softwareVersion(request.getVersion())
                .build();
        taskHandler.executeLoginLog(new LoginLogTask(record, loginLogService));
        return LoginTokenVO.builder().signKey(accessToken.getSignKey()).accessToken(accessToken.getAccessToken()).build();
    }


    @Override
    public User getByAccount(String account) {
        if (RegExpUtil.mobile(account)) {
            return userMapper.getByMobile(account);
        }
        return userMapper.getByEmail(account);
    }

    @Override
    public void loginSendSms(String mobile) {
        User user = this.getByAccountRequired(mobile);
        smsService.sendSmsCode(SmsTypeConstant.LOGIN_SMS, user.getMobile());
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
        smsService.sendSmsCode(SmsTypeConstant.REGISTER_SMS, mobile);
    }

    @Override
    public LoginTokenVO registerByMobile(RegisterUserRequest request) {
        this.registerRedoVerify(request.getMobile());
        smsService.verifySmsCode(SmsTypeConstant.REGISTER_SMS, request.getMobile(), request.getSmsCode());
        UserRegister register = DataUtil.copy(request, UserRegister.class);
        User user = this.doRegister(register);
        return this.doLogin(user, register.getRegisterIp());
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
