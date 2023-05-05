package com.eghm.service.business.handler.access;

import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;

import static com.eghm.service.pay.enums.RefundStatus.REFUND_SUCCESS;
import static com.eghm.service.pay.enums.RefundStatus.SUCCESS;

/**
 * @author wyb
 * @since 2023/5/5
 */

@AllArgsConstructor
public abstract class AbstractAccessHandler implements AccessHandler {

    private final OrderService orderService;

    private final AggregatePayService aggregatePayService;

    /**
     * 判断订单是否支付成功
     * @param context 订单异步回调信息
     * @return true: 成功 false:失败
     */
    public boolean checkPaySuccess(PayNotifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        context.setAmount(vo.getAmount());
        context.setSuccessTime(vo.getSuccessTime());
        context.setTradeType(vo.getTradeType());
        context.setFrom(order.getState().getValue());
        return  vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS;
    }

    /**
     * 判断订单是否退款成功
     * @param context 订单异步回调信息
     * @return true: 成功 false:失败
     */
    public boolean checkRefundSuccess(RefundNotifyContext context) {
        Order order = orderService.selectByOutTradeNo(context.getOutTradeNo());
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        RefundVO vo = aggregatePayService.queryRefund(tradeType, context.getOutTradeNo(), context.getOutRefundNo());
        context.setAmount(vo.getAmount());
        context.setSuccessTime(vo.getSuccessTime());
        context.setFrom(order.getState().getValue());
        return vo.getState() == REFUND_SUCCESS || vo.getState() == SUCCESS;
    }
}
