package com.eghm.state.machine.impl;

import com.eghm.enums.OrderState;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    @Override
    public void doAction(PayNotifyContext context) {
        List<Order> orderList = orderService.getByTradeNoList(context.getTradeNo());
        this.doProcess(context, orderList);
        this.after(orderList);
    }

    /**
     * 订单失败处理逻辑, 更新订单状态
     *
     * @param orderList 订单信息
     */
    protected void doProcess(PayNotifyContext context, List<Order> orderList) {
        for (Order order : orderList) {
            orderService.updateState(order.getOrderNo(), OrderState.UN_PAY, OrderState.of(context.getFrom()));
        }
    }

    /**
     * 订单异步通知后置处理
     *
     * @param orderList 订单列表
     */
    protected void after(List<Order> orderList) {
    }

}
