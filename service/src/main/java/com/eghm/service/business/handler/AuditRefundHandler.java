package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.AuditRefundContext;
import com.eghm.state.machine.ActionHandler;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface AuditRefundHandler extends ActionHandler<AuditRefundContext> {

    /**
     * 退款审核处理
     * @param context 审核参数
     */
    void doAction(AuditRefundContext context);

}
