package com.fanyin.service.user;

import com.fanyin.dao.model.user.User;
import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.user.UserRegister;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.ext.AccessToken;

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
    User register(UserRegister register);

    /**
     * 账号登陆 邮箱或密码登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    AccessToken accountLogin(AccountLoginRequest login);

    /**
     * 短信验证码+手机号登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    AccessToken smsLogin(SmsLoginRequest login);

    /**
     * 根据账号查询用户信息
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    User getByAccount(String account);

    /**
     * 根据主键查询用户信息
     * @param userId userId
     * @return 用户基本信息
     */
    User getById(Integer userId);

}
