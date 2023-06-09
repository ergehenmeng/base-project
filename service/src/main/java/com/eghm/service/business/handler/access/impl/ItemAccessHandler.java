package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AbstractAccessHandler;
import com.eghm.service.business.handler.context.*;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.StateHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/4/26
 */
@Service("itemAccessHandler")
public class ItemAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    private final OrderService orderService;

    public ItemAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
        this.orderService = orderService;
    }

    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.ITEM, OrderState.NONE.getValue(), ItemEvent.CREATE, context);
    }

    @Override
    public void refundApply(RefundApplyContext context) {
        Order order = orderService.selectById(context.getItemOrderId());
        stateHandler.fireEvent(ProductType.ITEM, order.getState().getValue(), ItemEvent.REFUND_APPLY, context);
    }

    @Override
    public void refundAudit(RefundAuditContext context) {
        if (context.getState() == 1) {
            stateHandler.fireEvent(ProductType.ITEM, OrderState.REFUND.getValue(), ItemEvent.REFUND_PASS, context);
        } else {
            stateHandler.fireEvent(ProductType.ITEM, OrderState.REFUND.getValue(), ItemEvent.REFUND_REFUSE, context);
        }
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.ITEM, OrderState.UN_USED.getValue(), ItemEvent.VERIFY, context);
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.REFUND_FAIL, context);
    }

}
