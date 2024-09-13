package com.eghm.state.machine.access.impl;

import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.*;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Service("ticketAccessHandler")
public class TicketAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    private final OrderService orderService;

    public TicketAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
        this.orderService = orderService;
    }

    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.TICKET, OrderState.NONE.getValue(), TicketEvent.CREATE, context);
    }

    @Override
    public void refundApply(RefundApplyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        stateHandler.fireEvent(ProductType.TICKET, order.getState().getValue(), TicketEvent.REFUND_APPLY, context);
    }

    @Override
    public void refundAudit(RefundAuditContext context) {
        if (context.getState() == 1) {
            stateHandler.fireEvent(ProductType.TICKET, OrderState.REFUND.getValue(), TicketEvent.REFUND_PASS, context);
        } else {
            stateHandler.fireEvent(ProductType.TICKET, OrderState.REFUND.getValue(), TicketEvent.REFUND_REFUSE, context);
        }
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.TICKET, OrderState.UN_USED.getValue(), TicketEvent.VERIFY, context);
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.TICKET, context.getFrom(), TicketEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.TICKET, context.getFrom(), TicketEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.TICKET, context.getFrom(), TicketEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.TICKET, context.getFrom(), TicketEvent.REFUND_FAIL, context);
    }

    @Override
    public void cancel(OrderCancelContext context) {
        stateHandler.fireEvent(ProductType.TICKET, OrderState.UN_PAY.getValue(), TicketEvent.CANCEL, context);
    }
}
