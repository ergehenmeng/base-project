package com.eghm.service.business.handler.access;

import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;

/**
 * @author wyb
 * @since 2023/4/26
 */
public interface AccessHandler {

    /**
     * 支付异步通知
     * @param context context
     */
    void payNotify(PayNotifyContext context);

    /**
     * 退款异步通知
     * @param context context
     */
    void refundNotify(RefundNotifyContext context);
}
