package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author 二哥很猛
 */
@Data
public class SystemOperatorRole implements Serializable {
    private static final long serialVersionUID = 987553406182629138L;
    /**
     * 主键<br>
     * 表 : system_operator_role<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户id<br>
     * 表 : system_operator_role<br>
     * 对应字段 : operator_id<br>
     */
    private Integer operatorId;

    /**
     * 角色id<br>
     * 表 : system_operator_role<br>
     * 对应字段 : role_id<br>
     */
    private Integer roleId;

    public SystemOperatorRole() {
    }

    public SystemOperatorRole(Integer operatorId, Integer roleId) {
        this.operatorId = operatorId;
        this.roleId = roleId;
    }
}