package com.eghm.service.user.impl;

import com.eghm.common.constant.SmsTypeConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.RegExpUtil;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.security.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.user.UserMapper;
import com.eghm.dao.model.user.User;
import com.eghm.model.dto.login.AccountLoginRequest;
import com.eghm.model.dto.login.SmsLoginRequest;
import com.eghm.model.dto.register.RegisterUserRequest;
import com.eghm.model.ext.*;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.queue.TaskHandler;
import com.eghm.queue.task.LoginLogTask;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.common.SmsService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.service.user.LoginLogService;
import com.eghm.service.user.UserExtService;
import com.eghm.service.user.UserService;
import com.eghm.utils.DataUtil;
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
