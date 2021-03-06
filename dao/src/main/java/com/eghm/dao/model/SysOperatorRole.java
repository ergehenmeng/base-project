package com.eghm.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author 二哥很猛
 */
@Data
@AllArgsConstructor
public class SysOperatorRole implements Serializable {
    private static final long serialVersionUID = 987553406182629138L;
    /**
     * 主键<br>
     * 表 : sys_operator_role<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 用户id<br>
     * 表 : sys_operator_role<br>
     * 对应字段 : operator_id<br>
     */
    private Long operatorId;

    /**
     * 角色id<br>
     * 表 : sys_operator_role<br>
     * 对应字段 : role_id<br>
     */
    private Long roleId;

    public SysOperatorRole() {
    }

}