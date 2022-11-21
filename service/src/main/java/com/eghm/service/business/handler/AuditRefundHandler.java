package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.AuditRefundDTO;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface AuditRefundHandler {

    /**
     * 退款审核处理
     * @param dto 审核参数
     */
    void process(AuditRefundDTO dto);
}
