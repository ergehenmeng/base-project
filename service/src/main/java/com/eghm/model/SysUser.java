
package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@TableName("sys_user")
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
     * 商户用户
     */
    public static final int USER_TYPE_2 = 2;

    @ApiModelProperty("用户姓名")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String nickName;

    @ApiModelProperty("手机号码(登陆账户)")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String mobile;

    @ApiModelProperty("用户类型 1: 系统用户 2: 商户用户")
    private Integer userType;

    @ApiModelProperty("用户状态:0:锁定,1:正常")
    private Integer state;

    @ApiModelProperty("登陆密码")
    @JsonIgnore
    private String pwd;

    @ApiModelProperty("初始密码")
    @JsonIgnore
    private String initPwd;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("权限类型")
    private Integer permissionType;

    @ApiModelProperty("备注信息")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty("用户的左侧菜单权限")
    private List<SysMenu> leftMenu;

    @TableField(exist = false)
    @ApiModelProperty("用户的按钮权限")
    private List<SysMenu> buttonMenu;

    @TableField(exist = false)
    @ApiModelProperty("用户的部门数据权限")
    private List<String> deptList;
}