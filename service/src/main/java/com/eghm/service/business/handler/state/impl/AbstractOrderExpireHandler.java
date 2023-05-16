package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.context.OrderCancelContext;
import com.eghm.service.business.handler.state.OrderExpireHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderExpireHandler implements OrderExpireHandler {

    private final OrderService orderService;

    private final UserCouponService userCouponService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(OrderCancelContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(order);

        this.doProcess(order);

        this.after(order);
    }


    /**
     * 过期后置处理, 例如释放库存等
     * @param order 订单
     */
    protected void after(Order order) {
        log.info("订单过期处理成功 [{}]", order.getOrderNo());
    }

    /**
     * 订单过期处理
     * 1.更新订单状态
     * 2.释放优惠券
     * @param order 订单
     */
    private void doProcess(Order order) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(CloseType.EXPIRE);
        order.setCloseTime(LocalDateTime.now());
        orderService.updateById(order);
        userCouponService.releaseCoupon(order.getCouponId());
    }

    protected void before(Order order) {
        if (order.getState() != OrderState.UN_PAY) {
            log.warn("订单状态不是待支付,无法自动取消订单 [{}] [{}]", order.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.ORDER_STATE_MATCH);
        }
    }

}
