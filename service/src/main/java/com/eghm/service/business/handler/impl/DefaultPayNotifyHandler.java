package com.eghm.service.business.handler.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.PayNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Service("defaultPayNotifyHandler")
@AllArgsConstructor
@Slf4j
public class DefaultPayNotifyHandler implements PayNotifyHandler {

    private final OrderService orderService;

    private final AggregatePayService aggregatePayService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Async
    public void process(String orderNo, String outTradeNo) {
        Order order = orderService.getByOrderNo(orderNo);
        this.before(order);

        boolean payState = this.doProcess(order);

        this.after(order, payState);
    }

    /**
     * 订单支付处理
     * 1.成功则则更新订单状态
     *
     * @param order 订单信息
     * @return 订单支付状态 true:成功 false:不成功(可能还在支付中)
     */
    private boolean doProcess(Order order) {
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        boolean payState = vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS;
        if (payState) {
            orderService.updateState(order.getOrderNo(), OrderState.UN_USED, OrderState.UN_PAY, OrderState.PROGRESS);
        }
        return payState;
    }

    /**
     * 订单异步通知后置处理
     *
     * @param order    订单信息
     * @param payState 订单支付状态 true:一定表示支付成功, false:没成功
     */
    protected void after(Order order, boolean payState) {
        log.info("订单支付结果 [{}] [{}]", order.getOrderNo(), payState);
    }

    /**
     * 支付异步通知前校验
     *
     * @param order 订单信息
     */
    protected void before(Order order) {
        if (order.getState() != OrderState.PROGRESS) {
            log.error("订单状态已更改,无须更新支付状态 [{}] [{}]", order.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
    }

}
