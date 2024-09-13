package com.eghm.state.machine.access.impl;

import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.*;
import com.eghm.service.business.handler.context.*;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.handler.context.*;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Service("homestayAccessHandler")
public class HomestayAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    private final OrderService orderService;

    public HomestayAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
        this.orderService = orderService;
    }

    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.NONE.getValue(), HomestayEvent.CREATE, context);
    }

    @Override
    public void refundApply(RefundApplyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        stateHandler.fireEvent(ProductType.HOMESTAY, order.getState().getValue(), HomestayEvent.REFUND_APPLY, context);
    }

    @Override
    public void refundAudit(RefundAuditContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        if (context.getState() == 1) {
            stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.REFUND.getValue(), HomestayEvent.REFUND_PASS, context);
        } else {
            stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.REFUND.getValue(), HomestayEvent.REFUND_REFUSE, context);
        }
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, context.getFrom(), HomestayEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, context.getFrom(), HomestayEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, context.getFrom(), HomestayEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, context.getFrom(), HomestayEvent.REFUND_FAIL, context);
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.UN_USED.getValue(), HomestayEvent.VERIFY, context);
    }

    @Override
    public void cancel(OrderCancelContext context) {
        stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.UN_PAY.getValue(), HomestayEvent.CANCEL, context);
    }
}
