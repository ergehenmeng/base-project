package com.eghm.state.machine.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.CloseType;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.pay.enums.TradeState;
import com.eghm.pay.enums.TradeType;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.OrderCancelContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 订单取消
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderCancelHandler implements ActionHandler<OrderCancelContext> {

    private final OrderService orderService;

    private final MemberCouponService memberCouponService;

    private final AggregatePayService aggregatePayService;

    @Override
    public void doAction(OrderCancelContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(order);
        this.doProcess(order);
        this.after(order);
    }

    /**
     * 订单取消成功后置处理, 例如库存释放
     *
     * @param order 订单信息
     */
    protected void after(Order order) {
        log.info("订单取消成功 [{}]", order.getId());
        PayType payType = order.getPayType();
        if (payType != null) {
            TradeType tradeType = TradeType.of(payType.name());
            aggregatePayService.closeOrder(tradeType, order.getTradeNo());
        }
    }

    /**
     * 关闭订单信息
     *
     * @param order 订单信息
     */
    private void doProcess(Order order) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(CloseType.CANCEL);
        order.setCloseTime(LocalDateTime.now());
        orderService.updateById(order);
        memberCouponService.releaseCoupon(order.getCouponId());
    }

    /**
     * 取消订单前值校验
     *
     * @param order 订单信息
     */
    protected void before(Order order) {
        if (order.getState() != OrderState.UN_PAY && order.getState() != OrderState.PROGRESS) {
            log.error("订单状态不是待支付 [{}] [{}]", order.getId(), order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        TradeState state = orderService.getOrderPayState(order);
        if (state != TradeState.NOT_PAY && state != TradeState.WAIT_BUYER_PAY) {
            log.error("订单已支付,无法直接关闭 [{}] [{}]", order.getId(), state);
            throw new BusinessException(ErrorCode.ORDER_PAID_CANCEL);
        }
    }
}
