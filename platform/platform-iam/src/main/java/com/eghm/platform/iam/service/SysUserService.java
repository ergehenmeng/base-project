package com.eghm.platform.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.iam.dto.SmsLoginRequest;
import com.eghm.platform.iam.dto.TotpBindRequest;
import com.eghm.platform.iam.dto.TotpCheckRequest;
import com.eghm.platform.iam.dto.*;
import com.eghm.foundation.core.enums.UserState;
import com.eghm.platform.iam.entity.SysUser;
import com.eghm.platform.iam.vo.LoginMenuResponse;
import com.eghm.platform.iam.vo.LoginResponse;
import com.eghm.platform.iam.vo.TotpLoginResponse;
import com.eghm.platform.iam.vo.UserResponse;

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
     * 删除用户
     *
     * @param id userId
     */
    void deleteById(Long id);

    /**
     * 锁定用户
     *
     * @param id    userId
     * @param state 用户状态
     */
    void updateState(Long id, UserState state);

    /**
     * 系统用户登陆平台
     *
     * @param userName 账号
     * @param password 密码
     * @param openId   openId
     * @param ip       ip地址
     * @return token及权限
     */
    TotpLoginResponse login(String userName, String password, String openId, String ip);

    /**
     * 验证双因子并登录
     *
     * @param request 验证码
     * @return 登录信息
     */
    LoginResponse checkTotp(TotpCheckRequest request);

    /**
     * 绑定双因子
     *
     * @param request 绑定信息
     * @return 登录信息
     */
    LoginResponse bindTotp(TotpBindRequest request);

    /**
     * 解绑微信
     */
    void unbindWeChat();

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
     * @param ip   ip地址
     * @return 返回前端信息
     */
    LoginResponse doLogin(SysUser user, String ip);

    /**
     * 获取当前登录人的菜单
     *
     * @return 菜单
     */
    LoginMenuResponse getPermission();

    /**
     * 解绑totp
     *
     * @param userId 用户ID
     */
    void unBindTotp(Long userId);

    /**
     * 更新头像
     *
     * @param userId 用户id
     * @param avatar 头像
     */
    void updateAvatar(Long userId, String avatar);

    /**
     * 更新用户基础信息
     *
     * @param request 用户信息
     */
    void updateProfile(UserProfileRequest request);
}

