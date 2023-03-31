package com.eghm.enums.event.impl;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.OrderState;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Getter
@AllArgsConstructor
public enum TicketEvent implements IEvent {

    /**
     * 创建订单
     */
    CREATE(Lists.newArrayList(OrderState.NONE.getValue()), OrderState.UN_PAY.getValue()),

    /**
     * 支付处理中
     */
    PAYING(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.PROGRESS.getValue()),

    /**
     * 支付成功
     */
    PAY_SUCCESS(Lists.newArrayList(OrderState.PROGRESS.getValue()), OrderState.UN_USED.getValue()),

    /**
     * 核销
     */
    VERIFY(Lists.newArrayList(OrderState.UN_USED.getValue()), OrderState.VERIFY.getValue()),

    /**
     * 确认
     */
    CONFIRM(Lists.newArrayList(OrderState.VERIFY.getValue()), OrderState.COMPLETE.getValue()),

    /**
     * 自动确认
     */
    AUTO_CONFIRM(Lists.newArrayList(OrderState.VERIFY.getValue()), OrderState.COMPLETE.getValue()),

    /**
     * 订单取消
     */
    CANCEL(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.CLOSE.getValue()),
    
    /**
     * 订单取消
     */
    AUTO_CANCEL(Lists.newArrayList(OrderState.UN_PAY.getValue()), OrderState.CLOSE.getValue()),


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
