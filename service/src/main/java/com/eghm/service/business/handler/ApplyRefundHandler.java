package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.ApplyRefundContext;
import com.eghm.state.machine.ActionHandler;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
public interface ApplyRefundHandler extends ActionHandler<ApplyRefundContext> {

    /**
     * 退款申请
     * @param context 退款申请参数
     */
    void doAction(ApplyRefundContext context);

}
