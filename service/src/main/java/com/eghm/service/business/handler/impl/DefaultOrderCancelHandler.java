package com.eghm.service.business.handler.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CloseType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.OrderCancelHandler;
import com.eghm.service.pay.enums.TradeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Service("defaultOrderCancelHandler")
@AllArgsConstructor
@Slf4j
public class DefaultOrderCancelHandler implements OrderCancelHandler {

    private final OrderService orderService;

    private final UserCouponService userCouponService;

    @Override
    public void process(String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
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
        order.setCloseType(CloseType.CANCEL);
        order.setCloseTime(LocalDateTime.now());
        orderService.updateById(order);
        userCouponService.releaseCoupon(order.getCouponId());
    }

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
