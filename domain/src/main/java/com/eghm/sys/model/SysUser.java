package com.eghm.sys.model;

import com.eghm.common.model.BaseEntity;

import com.eghm.enums.DataType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.UserState;
import com.eghm.enums.UserType;
import com.eghm.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    /** 头像 */
    private String avatar;

    /** 用户姓名 */
    private String nickName;

    /** 账户名(登陆账户) */
    private String userName;

    /** 手机号码(登陆账户) */
    private String mobile;

    /** 用户类型 0: 超级管理员 1: 系统用户 2: 商户管理员 3: 商户普通用户 */
    private UserType userType;

    /** 数据权限 只针对系统用户有效 */
    private DataType dataType;

    /** 用户状态:0:锁定,1:正常 2:注销 */
    private UserState state;

    /** 登陆密码 */
    private String pwd;

    /** 初始密码 */
    private String initPwd;

    /** 部门编号 */
    private String deptCode;

    /** 备注信息 */
    private String remark;

    /** totp秘钥 */
    private String totpSecret;

    /** 密码修改时间 */
    private LocalDateTime pwdUpdateTime;

    /** 微信openId */
    private String openId;

    /**
     * 初始化系统用户
     *
     * @param password 初始密码
     * @param now      密码更新时间
     */
    public void initializeSystemUser(String password, LocalDateTime now) {
        this.state = UserState.NORMAL;
        this.userType = UserType.SYS_USER;
        this.pwd = password;
        this.initPwd = password;
        this.pwdUpdateTime = now;
    }

    /**
     * 修改登录密码
     *
     * @param password 新密码
     * @param now      修改时间
     */
    public void changePassword(String password, LocalDateTime now) {
        this.pwd = password;
        this.pwdUpdateTime = now;
    }

    /**
     * 重置登录密码
     *
     * @param password 新密码
     * @param now      修改时间
     */
    public void resetPassword(String password, LocalDateTime now) {
        this.pwd = password;
        this.initPwd = password;
        this.pwdUpdateTime = now;
    }

    /**
     * 校验用户是否可用于密码登录
     */
    public void assertCanPasswordLogin() {
        if (state == UserState.LOGOUT) {
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        assertNotLocked();
    }

    /**
     * 校验用户是否可用于短信登录
     */
    public void assertCanSmsLogin() {
        if (state == UserState.LOGOUT) {
            throw new BusinessException(ErrorCode.USER_MOBILE_NULL);
        }
        assertNotLocked();
    }

    /**
     * 校验用户是否锁定
     */
    public void assertNotLocked() {
        if (state == UserState.LOCK) {
            throw new BusinessException(ErrorCode.USER_LOCKED_ERROR);
        }
    }

    /**
     * 绑定微信openId
     *
     * @param openId openId
     */
    public void bindOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 绑定TOTP秘钥
     *
     * @param secret 秘钥
     */
    public void bindTotp(String secret) {
        this.totpSecret = secret;
    }

    /**
     * 是否需要初始化密码提醒
     *
     * @return true:需要提醒
     */
    public boolean isInitialPassword() {
        return initPwd != null && initPwd.equals(pwd);
    }

    /**
     * 密码是否即将过期
     *
     * @param now  当前时间
     * @param days 提醒天数
     * @return true:即将过期
     */
    public boolean isPasswordExpire(LocalDateTime now, long days) {
        return pwdUpdateTime.plusDays(days).isBefore(now);
    }
}
