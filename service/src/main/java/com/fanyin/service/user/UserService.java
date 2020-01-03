package com.fanyin.service.user;

import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.dto.register.RegisterUserRequest;
import com.fanyin.model.ext.UserRegister;
import com.fanyin.model.vo.login.LoginTokenVO;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
public interface UserService {

    /**
     * 注册新用户,必须保证参数已校验,昵称如果为空默认由系统生成
     * @param register 用户注册信息
     * @return 注册后的用户信息
     */
    User doRegister(UserRegister register);

    /**
     * 账号登陆 邮箱或密码登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO accountLogin(AccountLoginRequest login);

    /**
     * 短信验证码+手机号登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO smsLogin(SmsLoginRequest login);

    /**
     * 根据账号查询用户信息
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    User getByAccount(String account);


    /**
     * 登陆发送验证码
     * @param mobile 手机号码
     */
    void loginSendSms(String mobile);

    /**
     * 根据账号查询用户信息(如果不存在,则抛异常)
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    User getByAccountRequired(String account);

    /**
     * 注册发送验证码
     * @param mobile 手机号码
     */
    void registerSendSms(String mobile);

    /**
     * 手机号+验证码注册
     * @param request 手机号及验证码信息
     * @return 注册后直接登陆
     */
    LoginTokenVO registerByMobile(RegisterUserRequest request);
}
