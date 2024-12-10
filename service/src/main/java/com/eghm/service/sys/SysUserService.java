package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.user.PasswordEditRequest;
import com.eghm.dto.sys.user.UserAddRequest;
import com.eghm.dto.sys.user.UserEditRequest;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.enums.ref.UserState;
import com.eghm.model.SysUser;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.user.UserResponse;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
public interface SysUserService {

    /**
     * 分页查询系统人员信息
     *
     * @param request 请求参数
     * @return 系统人员信息
     */
    Page<UserResponse> getByPage(UserQueryRequest request);

    /**
     * 更新登陆密码
     *
     * @param request 前台参数
     */
    void updateLoginPassword(PasswordEditRequest request);

    /**
     * 校验用户密码是否等于指定的密码
     *
     * @param userId      用户ID
     * @param rawPassword 用户输入的的密码
     */
    void checkPassword(Long userId, String rawPassword);

    /**
     * 添加管理人员 初始密码默认手机号后6位
     *
     * @param request 前台参数
     */
    void create(UserAddRequest request);

    /**
     * 添加管理人员
     *
     * @param user 管理人员
     */
    void insert(SysUser user);

    /**
     * 根据主键查询管理人员 不存在就抛异常
     *
     * @param id 主键
     * @return 用户信息
     */
    SysUser getByIdRequired(Long id);

    /**
     * 更新用户信息
     *
     * @param request 请求参数
     */
    void update(UserEditRequest request);

    /**
     * 重置用户登录密码 默认手机号后六位
     *
     * @param id 系统用户id
     */
    void resetPassword(Long id);

    /**
     * 重置密码
     *
     * @param id  id
     * @param pwd 新密码
     */
    void resetPassword(Long id, String pwd);

    /**
     * 删除用户
     *
     * @param id userId
     */
    void deleteById(Long id);

    /**
     * 锁定用户
     *
     * @param id userId
     * @param state 用户状态
     */
    void updateState(Long id, UserState state);

    /**
     * 系统用户登陆平台
     *
     * @param userName 账号
     * @param password 密码
     * @param openId   openId
     * @return token及权限
     */
    LoginResponse login(String userName, String password, String openId);

    /**
     * 登陆发送验证码
     *
     * @param mobile 手机号码
     * @param ip     ip地址
     */
    void sendLoginSms(String mobile, String ip);

    /**
     * 短信登陆管理后台
     *
     * @param request 请求信息
     * @param openId  openId
     * @return 响应信息
     */
    LoginResponse smsLogin(SmsLoginRequest request, String openId);

    /**
     * 更新用户信息
     *
     * @param user user
     */
    void updateById(SysUser user);

    /**
     * 根据openId获取用户信息
     *
     * @param openId openId
     * @return 用户信息
     */
    SysUser getByOpenId(String openId);

    /**
     * 管理后台登陆
     *
     * @param user 用户信息
     * @return 返回前端信息
     */
    LoginResponse doLogin(SysUser user);

    /**
     * 解绑微信
     */
    void unbindWeChat();
}

