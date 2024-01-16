package com.eghm.service.business.handler.access;

import com.eghm.service.business.handler.context.*;
import com.eghm.state.machine.Context;

/**
 * @author wyb
 * @since 2023/4/26
 */
public interface AccessHandler {

    /**
     * 创建支付订单
     *
     * @param context 订单信息
     */
    void createOrder(Context context);

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
     * 退款申请
     *
     * @param context context
     */
    void refundApply(RefundApplyContext context);

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
}
