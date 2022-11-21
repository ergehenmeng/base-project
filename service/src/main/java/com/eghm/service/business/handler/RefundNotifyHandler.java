package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.RefundNotifyContext;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface RefundNotifyHandler {

    /**
     * 退款回调异步处理
     * @param dto 支付及退款流水号
     */
    void process(RefundNotifyContext dto);
}
