package com.eghm.service.business.handler.access;

import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
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
        // 零售订单可能会查询多条 取第一条即可
        Order order = orderService.selectByOutTradeNo(context.getOutTradeNo());
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

    @Override
    public void payNotify(PayNotifyContext context) {
        boolean paySuccess = this.checkPaySuccess(context);
        if (paySuccess) {
            this.paySuccess(context);
        } else {
            this.payFail(context);
        }
    }

    @Override
    public void refundNotify(RefundNotifyContext context) {
        boolean refundSuccess = this.checkRefundSuccess(context);
        if (refundSuccess) {
            this.refundSuccess(context);
        } else {
            this.refundFail(context);
        }
    }

    /**
     * 支付成功处理
     * @param context 上下文
     */
    public abstract void paySuccess(PayNotifyContext context);

    /**
     * 支付失败处理
     * @param context 上下文
     */
    public abstract void payFail(PayNotifyContext context);

    /**
     * 退款成功处理
     * @param context 上下文
     */
    public abstract void refundSuccess(RefundNotifyContext context);

    /**
     * 退款失败处理
     * @param context 上下文
     */
    public abstract void refundFail(RefundNotifyContext context);

}
