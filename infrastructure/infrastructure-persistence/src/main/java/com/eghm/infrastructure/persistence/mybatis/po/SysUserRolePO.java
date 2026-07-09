package com.eghm.infrastructure.persistence.mybatis.po;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_user_role")
@AllArgsConstructor
public class SysUserRolePO {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;

    public SysUserRolePO(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

