package com.eghm.state.machine.access.impl;

import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.*;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/4/26
 */
@Service("itemAccessHandler")
public class ItemAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    public ItemAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
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

    @Override
    public void cancel(OrderCancelContext context) {
        stateHandler.fireEvent(ProductType.ITEM, OrderState.UN_PAY.getValue(), ItemEvent.CANCEL, context);
    }
}
