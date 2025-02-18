package com.eghm.dao.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AuditRecord extends BaseEntity {

    /**
     * 审批单号(在整个审批流程中不变)<br>
     * 表 : audit_record<br>
     * 对应字段 : audit_no<br>
     */
    private String auditNo;

    /**
     * 审核状态 0:待审核 1:审批通过 2:审批拒绝<br>
     * 表 : audit_record<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 审批意见<br>
     * 表 : audit_record<br>
     * 对应字段 : opinion<br>
     */
    private String opinion;

    /**
     * 审核信息标题<br>
     * 表 : audit_record<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 关联该审批的申请id<br>
     * 表 : audit_record<br>
     * 对应字段 : apply_id<br>
     */
    private Long applyId;

    /**
     * 申请人userId<br>
     * 表 : audit_record<br>
     * 对应字段 : apply_operator_id<br>
     */
    private Long applyOperatorId;

    /**
     * 申请人姓名<br>
     * 表 : audit_record<br>
     * 对应字段 : apply_operator_name<br>
     */
    private String applyOperatorName;

    /**
     * 审核人角色类型<br>
     * 表 : audit_record<br>
     * 对应字段 : role_type<br>
     */
    private String roleType;

    /**
     * 审核类型<br>
     * 表 : audit_record<br>
     * 对应字段 : audit_type<br>
     */
    private String auditType;

    /**
     * 当前审核的步骤<br>
     * 表 : audit_record<br>
     * 对应字段 : step<br>
     */
    private Byte step;

    /**
     * 审核人id<br>
     * 表 : audit_record<br>
     * 对应字段 : audit_operator_id<br>
     */
    private Long auditOperatorId;

    /**
     * 审核人姓名<br>
     * 表 : audit_record<br>
     * 对应字段 : audit_operator_name<br>
     */
    private String auditOperatorName;

    /**
     * 审核时间<br>
     * 表 : audit_record<br>
     * 对应字段 : audit_time<br>
     */
    private Date auditTime;

    /**
     * 添加时间<br>
     * 表 : audit_record<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : audit_record<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}