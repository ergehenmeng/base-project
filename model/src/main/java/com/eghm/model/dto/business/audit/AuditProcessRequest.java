package com.eghm.model.dto.business.audit;

import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.AuditType;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/8/27
 */
@Data
public class AuditProcessRequest {

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

}
