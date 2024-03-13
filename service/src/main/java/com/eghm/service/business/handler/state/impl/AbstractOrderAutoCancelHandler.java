package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.model.Order;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.context.OrderCancelContext;
import com.eghm.service.business.handler.state.OrderAutoCancelHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 订单自动取消 默认实现
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderAutoCancelHandler implements OrderAutoCancelHandler {

    private final OrderService orderService;

    private final MemberCouponService memberCouponService;

    private final AggregatePayService aggregatePayService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
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
