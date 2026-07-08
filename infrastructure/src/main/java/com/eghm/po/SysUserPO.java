package com.eghm.po;

import com.eghm.enums.DataType;
import com.eghm.enums.UserState;
import com.eghm.enums.UserType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUserPO extends BaseEntityPO {

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
}


