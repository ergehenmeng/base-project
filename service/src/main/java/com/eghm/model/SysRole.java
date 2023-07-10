package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.RoleType;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    @ApiModelProperty("角色名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String roleName;

    @ApiModelProperty("角色类型")
    private RoleType roleType;

    @ApiModelProperty("备注信息")
    private String remark;

}