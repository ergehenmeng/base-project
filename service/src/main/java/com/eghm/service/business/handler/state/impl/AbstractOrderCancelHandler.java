package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.context.OrderCancelContext;
import com.eghm.service.business.handler.state.OrderAutoCancelHandler;
import com.eghm.service.business.handler.state.OrderCancelHandler;
import com.eghm.service.pay.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderCancelHandler implements OrderCancelHandler, OrderAutoCancelHandler {

    private final OrderService orderService;

    private final UserCouponService userCouponService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doAction(OrderCancelContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(order);
        this.doProcess(order);
        this.after(order);
    }

    /**
     * 订单取消成功后置处理, 例如库存释放
     * @param order 订单信息
     */
    protected void after(Order order) {
        log.info("订单取消成功 [{}]", order.getId());
    }

    /**
     * 关闭订单信息
     * @param order 订单信息
     */
    private void doProcess(Order order) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(this.getCloseType());
        order.setCloseTime(LocalDateTime.now());
        orderService.updateById(order);
        userCouponService.releaseCoupon(order.getCouponId());
    }

    /**
     * 取消订单的方式
     * @return 类型
     */
    public abstract CloseType getCloseType();

    /**
     * 取消订单前值校验
     * @param order 订单信息
     */
    protected void before(Order order) {
        if (order.getState() != OrderState.UN_PAY) {
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
