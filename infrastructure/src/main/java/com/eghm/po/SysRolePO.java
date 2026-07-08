package com.eghm.po;

import com.eghm.enums.RoleType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRolePO extends BaseEntityPO {

    /** 角色名称 */
    private String roleName;

    /** 角色类型 */
    private RoleType roleType;

    /** 备注信息 */
    private String remark;

}


