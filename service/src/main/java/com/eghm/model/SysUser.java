package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.DataType;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    /**
     * 正常
     */
    public static final int STATE_1 = 1;

    /**
     * 锁定
     */
    public static final int STATE_0 = 0;

    /**
     * 系统用户
     */
    public static final int USER_TYPE_1 = 1;

    /**
     * 商户管理员
     */
    public static final int USER_TYPE_2 = 2;

    /**
     * 商户普通用户
     */
    public static final int USER_TYPE_3 = 3;

    @ApiModelProperty("用户姓名")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String nickName;

    @ApiModelProperty("手机号码(登陆账户)")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String mobile;

    @ApiModelProperty("用户类型 1: 系统用户 2: 商户管理员 3: 商户普通用户")
    private Integer userType;

    @ApiModelProperty("数据权限 只针对系统用户有效")
    private DataType dataType;

    @ApiModelProperty("用户状态:0:锁定,1:正常 2:注销")
    private Integer state;

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

}