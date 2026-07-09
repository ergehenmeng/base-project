package com.eghm.sys.model;

import com.eghm.model.BaseEntity;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleType;
import com.eghm.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    /** 角色名称 */
    private String roleName;

    /** 角色类型 */
    private RoleType roleType;

    /** 备注信息 */
    private String remark;

    /**
     * 校验角色是否允许删除
     */
    public void assertDeletable() {
        if (roleType != RoleType.COMMON) {
            throw new BusinessException(ErrorCode.ROLE_FORBID_DELETE);
        }
    }

    /**
     * 修改角色基础信息
     *
     * @param roleName 角色名称
     * @param remark   备注信息
     */
    public void changeProfile(String roleName, String remark) {
        this.roleName = roleName;
        this.remark = remark;
    }

    public void initialize(String roleName, RoleType roleType) {
        this.roleName = roleName;
        this.roleType = roleType;
    }
}
