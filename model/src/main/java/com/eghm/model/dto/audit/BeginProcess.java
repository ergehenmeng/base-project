package com.eghm.model.dto.audit;

import com.eghm.common.enums.AuditType;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/8/25
 */

@Data
public class BeginProcess {

    /**
     * 申请信息id
     */
    private Long applyId;

    /**
     * 审核类型
     */
    private AuditType auditType;

    /**
     * 审批标题信息
     */
    private String title;

    /**
     * 申请人id
     */
    private Long applyOperatorId;

    /**
     * 申请人姓名
     */
    private String applyOperatorName;

}
