package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.state.PayNotifyHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付异步回调 成功
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderPaySuccessHandler implements PayNotifyHandler {

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(PayNotifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());

        this.doProcess(context, order);

        this.after(context, order);
    }

    /**
     * 订单成功
     *
     * @param order 订单信息
     */
    protected void doProcess(PayNotifyContext context, Order order) {
        orderService.paySuccess(order.getOrderNo(), order.getProductType().generateVerifyNo(), OrderState.UN_USED, OrderState.of(context.getFrom()));
    }

    /**
     * 订单异步通知后置处理
     *
     * @param context 支付成功异步通知
     * @param order   订单信息
     */
    protected void after(PayNotifyContext context, Order order) {
        orderVisitorService.updateVisitor(order.getOrderNo(), VisitorState.PAID);
    }

}
