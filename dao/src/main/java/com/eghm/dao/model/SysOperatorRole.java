package com.eghm.dao.model;

import lombok.*;


/**
 *
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysOperatorRole extends BaseEntity {


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

}