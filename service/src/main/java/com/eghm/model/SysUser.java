package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.DataType;
import com.eghm.enums.UserType;
import com.eghm.enums.UserState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户姓名")
    private String nickName;

    @ApiModelProperty("账户名(登陆账户)")
    private String userName;

    @ApiModelProperty("手机号码(登陆账户)")
    private String mobile;

    @ApiModelProperty("用户类型 0: 超级管理员 1: 系统用户 2: 商户管理员 3: 商户普通用户")
    private UserType userType;

    @ApiModelProperty("数据权限 只针对系统用户有效")
    private DataType dataType;

    @ApiModelProperty("用户状态:0:锁定,1:正常 2:注销")
    private UserState state;

    @ApiModelProperty("登陆密码")
    @JsonIgnore
    private String pwd;

    @ApiModelProperty("初始密码")
    @JsonIgnore
    private String initPwd;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("密码修改时间")
    private LocalDateTime pwdUpdateTime;
}