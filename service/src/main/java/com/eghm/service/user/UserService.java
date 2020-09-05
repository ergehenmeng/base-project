package com.eghm.service.user;

import com.eghm.dao.model.User;
import com.eghm.model.dto.login.AccountLoginDTO;
import com.eghm.model.dto.login.SmsLoginDTO;
import com.eghm.model.dto.register.RegisterUserDTO;
import com.eghm.model.dto.user.BindEmailDTO;
import com.eghm.model.dto.user.ChangeEmailDTO;
import com.eghm.model.dto.user.SendEmailAuthCodeDTO;
import com.eghm.model.dto.user.UserAuthDTO;
import com.eghm.model.ext.UserRegister;
import com.eghm.model.vo.login.LoginTokenVO;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
public interface UserService {

    /**
     * 主键查询
     * @param userId id
     * @return user
     */
    User getById(Integer userId);

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
    LoginTokenVO accountLogin(AccountLoginDTO login);

    /**
     * 短信验证码+手机号登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO smsLogin(SmsLoginDTO login);

    /**
     * 根据账号查询用户信息
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    User getByAccount(String account);

    /**
     * 更新用户状态
     * 1.更新状态
     * 2.清除用户登陆状态
     *
     * @param userId 用户id
     * @param state  新状态 true:解冻 false:冻结
     */
    void updateState(Integer userId,Boolean state);

    /**
     * 登陆发送验证码
     * @param mobile 手机号码
     */
    void sendLoginSms(String mobile);

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
    LoginTokenVO registerByMobile(RegisterUserDTO request);

    /**
     * 强制将用户踢下线  (仅适用于移动端用户)
     * 1.增加一条用户被踢下线的记录
     * 2.清空之前用户登陆的信息
     * @param userId userId
     */
    void offline(int userId);

    /**
     * 绑定邮箱 发送邮件验证码 (1)
     * @param email  邮箱
     * @param userId 用户id
     */
    void sendBindEmail(String email, Integer userId);

    /**
     * 绑定邮箱  (2)
     * @param request 邮箱信息
     */
    void bindEmail(BindEmailDTO request);

    /**
     * 用户实名制认证
     * @param request 实名制信息
     */
    void realNameAuth(UserAuthDTO request);

    /**
     * 查看邮箱是会否被占用
     * @param email 邮箱号
     */
    void checkEmail(String email);

    /**
     * 更新邮箱发送短信验证码
     * @param userId userId
     */
    void sendChangeEmailSms(Integer userId);

    /**
     * 发送更换邮箱的邮件信息(邮件内容为验证码)
     * @param request 前台参数
     */
    void sendChangeEmailCode(SendEmailAuthCodeDTO request);

    /**
     * 换绑邮箱
     * @param request 新邮箱信息
     */
    void changeEmail(ChangeEmailDTO request);

    /**
     * 用户签到
     * @param userId 用户id
     */
    void signIn(Integer userId);

    /**
     * 获取今天签到状态
     * @param user user信息
     * @return true 已签到 false 未签到
     */
    Boolean getSignInToday(User user);
}
