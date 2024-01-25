package com.eghm.enums.event.impl;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.OrderState;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author wyb
 * @since 2023/4/26
 */
@Getter
@AllArgsConstructor
public enum ItemEvent implements IEvent {

    /**
     * 创建订单
     */
    CREATE(Lists.newArrayList(OrderState.NONE.getValue()), OrderState.UN_PAY.getValue()),

    /**
     * 创建订单 队列模式
     */
    CREATE_QUEUE(Lists.newArrayList(OrderState.NONE.getValue()), OrderState.UN_PAY.getValue()),

    /**
     * 支付处理中
     */
    PAYING(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.PROGRESS.getValue()),

    /**
     * 支付成功
     */
    PAY_SUCCESS(Lists.newArrayList(OrderState.PROGRESS.getValue()), OrderState.UN_USED.getValue()),

    /**
     * 退款申请
     */
    REFUND_APPLY(Lists.newArrayList(OrderState.PROGRESS.getValue(), OrderState.UN_USED.getValue()), OrderState.REFUND.getValue()),

    /**
     * 核销
     */
    VERIFY(Lists.newArrayList(OrderState.WAIT_TAKE.getValue()), OrderState.COMPLETE.getValue()),

    /**
     * 确认
     */
    CONFIRM(Lists.newArrayList(OrderState.WAIT_RECEIVE.getValue()), OrderState.COMPLETE.getValue()),

    /**
     * 订单取消
     */
    CANCEL(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.CLOSE.getValue()),

    /**
     * 订单取消
     */
    AUTO_CANCEL(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.CLOSE.getValue()),

    /**
     * 退款成功
     */
    REFUND_SUCCESS(Lists.newArrayList(OrderState.REFUND.getValue()), OrderState.CLOSE.getValue()),

    /**
     * 支付失败
     */
    PAY_FAIL(Lists.newArrayList(OrderState.PROGRESS.getValue()), OrderState.PAY_ERROR.getValue()),

    /**
     * 退款失败
     */
    REFUND_FAIL(Lists.newArrayList(OrderState.REFUND.getValue()), OrderState.REFUND_ERROR.getValue()),

    /**
     * 退款审核通过
     */
    REFUND_PASS(Lists.newArrayList(OrderState.REFUND.getValue()), OrderState.CLOSE.getValue()),

    /**
     * 退款审核拒绝
     */
    REFUND_REFUSE(Lists.newArrayList(OrderState.REFUND.getValue()), OrderState.CLOSE.getValue()),

    /**
     * 平台退款
     */
    PLATFORM_REFUND(Lists.newArrayList(OrderState.PROGRESS.getValue(), OrderState.UN_USED.getValue()), OrderState.CLOSE.getValue()),
    ;

    private final List<Integer> from;

    private final Integer to;

    @Override
    public List<Integer> from() {
        return from;
    }

    @Override
    public Integer to() {
        return to;
    }
}
