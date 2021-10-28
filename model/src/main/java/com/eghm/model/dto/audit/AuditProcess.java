package com.eghm.model.dto.audit;

import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.AuditType;
import com.eghm.model.annotation.Label;
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
    private Long id;

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
    @Label
    private Long auditOperatorId;

    /**
     * 审核人姓名
     */
    @Label
    private String auditOperatorName;

}
