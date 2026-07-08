package com.eghm.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@AllArgsConstructor
public class SysUserRole {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;

    public SysUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
