package com.eghm.process.dto;

import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.AuditType;
import com.eghm.configuration.annotation.BackstageTag;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@Data
public class AuditProcess {

    /**
     * 审核记录id
     */
    private Integer id;

    /**
     * 审核类型
     */
    private AuditType auditType;

    /**
     * 审核状态
     */
    private AuditState state;

    /**
     * 审核意见
     */
    private String opinion;

    /**
     * 审核人id
     */
    @BackstageTag
    private Integer auditOperatorId;

    /**
     * 审核人姓名
     */
    @BackstageTag
    private String auditOperatorName;

}
