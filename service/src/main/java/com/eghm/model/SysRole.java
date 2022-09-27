package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.RoleType;
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
    private String roleName;

    @ApiModelProperty("角色类型")
    private RoleType roleType;

    @ApiModelProperty("备注信息")
    private String remark;

}