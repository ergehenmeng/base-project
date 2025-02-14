package com.eghm.state.machine.access;

import com.eghm.model.Order;
import com.eghm.pay.service.AggregatePayService;
import com.eghm.pay.enums.RefundStatus;
import com.eghm.pay.enums.TradeState;
import com.eghm.pay.enums.TradeType;
import com.eghm.pay.vo.PayOrderVO;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.context.PayNotifyContext;
import com.eghm.state.machine.context.RefundNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.eghm.pay.enums.RefundStatus.ABNORMAL;
import static com.eghm.pay.enums.RefundStatus.CLOSED;
import static com.eghm.pay.enums.TradeState.PAY_ERROR;
import static com.eghm.pay.enums.TradeState.TRADE_CLOSED;

/**
 * 支付处理handler
 *
 * @author wyb
 * @since 2023/5/5
 */

@Slf4j
@AllArgsConstructor
public abstract class AbstractAccessHandler implements AccessHandler {

    private final OrderService orderService;

    private final AggregatePayService aggregatePayService;

    @Override
    public void payNotify(PayNotifyContext context) {
        TradeState paySuccess = this.checkPaySuccess(context);
        if (paySuccess == TradeState.SUCCESS || paySuccess == TradeState.TRADE_SUCCESS || paySuccess == TradeState.TRADE_FINISHED) {
            log.info("订单支付成功,开始执行业务逻辑 [{}] [{}]", context.getOrderNo(), paySuccess);
            this.paySuccess(context);
            return;
        }
        if (paySuccess == TradeState.CLOSED || paySuccess == TradeState.NOT_PAY || paySuccess == PAY_ERROR || paySuccess == TRADE_CLOSED) {
            log.info("订单支付失败,开始执行业务逻辑 [{}] [{}]", context.getOrderNo(), paySuccess);
            this.payFail(context);
            return;
        }
        log.warn("订单支付状态处理中,不做业务处理 [{}] [{}]", context.getOrderNo(), paySuccess);
    }

    @Override
    public void refundNotify(RefundNotifyContext context) {
        Order order = orderService.getByTradeNo(context.getTradeNo());
        context.setFrom(order.getState().getValue());
        RefundStatus refundSuccess = context.getResult().getState();
        if (refundSuccess == RefundStatus.REFUND_SUCCESS || refundSuccess == RefundStatus.SUCCESS) {
            log.info("订单退款支付成功,开始执行业务逻辑 [{}] [{}]", context.getTradeNo(), refundSuccess);
            this.refundSuccess(context);
            return;
        }
        if (refundSuccess == ABNORMAL || refundSuccess == CLOSED) {
            log.info("订单退款失败成功,开始执行业务逻辑 [{}] [{}]", context.getTradeNo(), refundSuccess);
            this.refundFail(context);
            return;
        }
        log.warn("订单退款状态处理中,不做业务处理 [{}]", context.getTradeNo());
    }

    /**
     * 判断订单是否支付成功
     *
     * @param context 订单异步回调信息
     * @return true: 成功 false:失败
     */
    public TradeState checkPaySuccess(PayNotifyContext context) {
        // 零售订单可能会查询多条 取第一条即可
        Order order = orderService.getByTradeNo(context.getTradeNo());
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        PayOrderVO vo = aggregatePayService.queryOrder(tradeType, order.getTradeNo());
        context.setAmount(vo.getAmount());
        context.setSuccessTime(vo.getSuccessTime());
        context.setTradeType(vo.getTradeType());
        context.setFrom(order.getState().getValue());
        return vo.getTradeState();
    }

    /**
     * 支付成功处理
     *
     * @param context 上下文
     */
    public abstract void paySuccess(PayNotifyContext context);

    /**
     * 支付失败处理
     *
     * @param context 上下文
     */
    public abstract void payFail(PayNotifyContext context);

    /**
     * 退款成功处理
     *
     * @param context 上下文
     */
    public abstract void refundSuccess(RefundNotifyContext context);

    /**
     * 退款失败处理
     *
     * @param context 上下文
     */
    public abstract void refundFail(RefundNotifyContext context);

}
