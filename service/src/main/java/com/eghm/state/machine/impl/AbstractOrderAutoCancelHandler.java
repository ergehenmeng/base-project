package com.eghm.state.machine.impl;

import com.eghm.enums.CloseType;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.model.Order;
import com.eghm.pay.service.AggregatePayService;
import com.eghm.pay.enums.TradeType;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.OrderCancelContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 订单自动取消 默认实现
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderAutoCancelHandler implements ActionHandler<OrderCancelContext> {

    private final OrderService orderService;

    private final MemberCouponService memberCouponService;

    private final AggregatePayService aggregatePayService;

    @Override
    public void doAction(OrderCancelContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        if (order.getState() == OrderState.CLOSE) {
            log.info("订单已关闭, 无需重复退款 [{}]", order.getOrderNo());
            return;
        }
        this.doProcess(order);
        this.after(order);
    }

    /**
     * 过期后置处理, 例如释放库存等
     *
     * @param order 订单
     */
    protected void after(Order order) {
        log.info("订单过期处理成功 [{}]", order.getOrderNo());
        PayType payType = order.getPayType();
        if (payType != null) {
            TradeType tradeType = TradeType.of(payType.name());
            aggregatePayService.closeOrder(tradeType, order.getTradeNo());
        }
    }

    /**
     * 订单过期处理
     * 1.更新订单状态
     * 2.释放优惠券
     *
     * @param order 订单
     */
    private void doProcess(Order order) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(CloseType.EXPIRE);
        order.setCloseTime(LocalDateTime.now());
        orderService.updateById(order);
        memberCouponService.releaseCoupon(order.getCouponId());
    }

}
