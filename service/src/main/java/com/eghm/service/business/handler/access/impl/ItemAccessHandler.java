package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AccessHandler;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.RefundVO;
import com.eghm.state.machine.StateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.service.pay.enums.RefundStatus.REFUND_SUCCESS;
import static com.eghm.service.pay.enums.RefundStatus.SUCCESS;

/**
 * @author wyb
 * @since 2023/4/26
 */
@Service("itemAccessHandler")
@AllArgsConstructor
public class ItemAccessHandler implements AccessHandler {

    private final StateHandler stateHandler;

    private final OrderService orderService;

    private final AggregatePayService aggregatePayService;

    @Override
    public void createOrder(ItemOrderCreateContext context) {
        stateHandler.fireEvent(ProductType.ITEM, OrderState.NONE.getValue(), ItemEvent.CREATE, context);
    }

    @Override
    public void payNotify(PayNotifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        context.setAmount(vo.getAmount());
        context.setSuccessTime(vo.getSuccessTime());
        context.setTradeType(vo.getTradeType());
        boolean paySuccess = vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS;
        if (paySuccess) {
            stateHandler.fireEvent(ProductType.ITEM, order.getState().getValue(), ItemEvent.PAY_SUCCESS, context);
        } else {
            stateHandler.fireEvent(ProductType.ITEM, order.getState().getValue(), ItemEvent.PAY_FAIL, context);
        }
    }

    @Override
    public void refundNotify(RefundNotifyContext context) {
        Order order = orderService.selectByOutTradeNo(context.getOutTradeNo());
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        RefundVO vo = aggregatePayService.queryRefund(tradeType, context.getOutTradeNo(), context.getOutRefundNo());
        context.setAmount(vo.getAmount());
        context.setSuccessTime(vo.getSuccessTime());
        boolean refundSuccess = vo.getState() == REFUND_SUCCESS || vo.getState() == SUCCESS;
        if (refundSuccess) {
            stateHandler.fireEvent(ProductType.ITEM, order.getState().getValue(), ItemEvent.REFUND_SUCCESS, context);
        } else {
            stateHandler.fireEvent(ProductType.ITEM, order.getState().getValue(), ItemEvent.REFUND_FAIL, context);
        }
    }
}
