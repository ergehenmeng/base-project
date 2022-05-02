
package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
public class SysOperator extends BaseEntity {

    /**
     * 正常
     */
    public static final byte STATE_1 = 1;

    /**
     * 锁定
     */
    public static final byte STATE_0 = 0;

    @ApiModelProperty("用户姓名")
    private String operatorName;

    @ApiModelProperty("手机号码(登陆账户)")
    private String mobile;

    @ApiModelProperty("用户状态:0:锁定,1:正常")
    private Byte state;

    @ApiModelProperty("登陆密码")
    private String pwd;

    @ApiModelProperty("初始密码")
    private String initPwd;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("权限类型")
    private Byte permissionType;

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