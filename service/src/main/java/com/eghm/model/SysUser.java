package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.DataType;
import com.eghm.enums.UserState;
import com.eghm.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    @Schema(description = "用户姓名")
    private String nickName;

    @Schema(description = "账户名(登陆账户)")
    private String userName;

    @Schema(description = "手机号码(登陆账户)")
    private String mobile;

    @Schema(description = "用户类型 0: 超级管理员 1: 系统用户 2: 商户管理员 3: 商户普通用户")
    private UserType userType;

    @Schema(description = "数据权限 只针对系统用户有效")
    private DataType dataType;

    @Schema(description = "用户状态:0:锁定,1:正常 2:注销")
    private UserState state;

    @Schema(description = "登陆密码")
    @JsonIgnore
    private String pwd;

    @Schema(description = "初始密码")
    @JsonIgnore
    private String initPwd;

    @Schema(description = "部门编号")
    private String deptCode;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "微信openId")
    private String openId;

    @Schema(description = "密码修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pwdUpdateTime;
}