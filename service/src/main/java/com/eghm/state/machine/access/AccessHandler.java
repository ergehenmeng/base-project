package com.eghm.state.machine.access;

import com.eghm.state.machine.context.*;

/**
 * @author wyb
 * @since 2023/4/26
 */
public interface AccessHandler {

    /**
     * 支付异步通知
     *
     * @param context context
     */
    void payNotify(PayNotifyContext context);

    /**
     * 退款异步通知
     *
     * @param context context
     */
    void refundNotify(RefundNotifyContext context);

    /**
     * 退款审核
     *
     * @param context context
     */
    void refundAudit(RefundAuditContext context);

    /**
     * 订单核销
     *
     * @param context context
     */
    void verifyOrder(OrderVerifyContext context);

    /**
     * 订单取消
     *
     * @param context 取消订单
     */
    void cancel(OrderCancelContext context);
}
