package com.eghm.state.machine.impl;

import com.eghm.enums.ref.OrderState;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * 支付异步回调 失败
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderPayFailHandler implements ActionHandler<PayNotifyContext> {

    private final OrderService orderService;

    @Async
    @Override
    public void doAction(PayNotifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());

        this.doProcess(context, order);

        this.after(context, order);
    }

    /**
     * 订单失败处理逻辑, 更新订单状态
     *
     * @param order 订单信息
     */
    protected void doProcess(PayNotifyContext context, Order order) {
        orderService.updateState(order.getOrderNo(), OrderState.UN_PAY, OrderState.of(context.getFrom()));
    }

    /**
     * 订单异步通知后置处理
     *
     * @param context 支付失败异步通知
     * @param order   订单信息
     */
    protected void after(PayNotifyContext context, Order order) {
    }

}
