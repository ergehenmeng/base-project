package com.eghm.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuditConfig implements Serializable {
    /**
     * 主键<br>
     * 表 : audit_config<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 审核类型<br>
     * 表 : audit_config<br>
     * 对应字段 : audit_type<br>
     */
    private String auditType;

    /**
     * 角色类型<br>
     * 表 : audit_config<br>
     * 对应字段 : role_type<br>
     */
    private String roleType;

    /**
     * 审批时的步骤(1,2,3)<br>
     * 表 : audit_config<br>
     * 对应字段 : step<br>
     */
    private Byte step;

    /**
     * 拒绝策略 1:结束流程 2:退回上一步 3:退回第一步<br>
     * 表 : audit_config<br>
     * 对应字段 : rejection_policy<br>
     */
    private Long rejectionPolicy;

    /**
     * 添加时间<br>
     * 表 : audit_config<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : audit_config<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}