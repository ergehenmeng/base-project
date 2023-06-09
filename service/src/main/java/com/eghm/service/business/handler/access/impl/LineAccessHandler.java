package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.LineEvent;
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
 * @since 2023/5/8
 */
@Service("lineAccessHandler")
public class LineAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    private final OrderService orderService;

    public LineAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
        this.orderService = orderService;
    }
    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.LINE, OrderState.NONE.getValue(), LineEvent.CREATE, context);
    }

    @Override
    public void refundApply(RefundApplyContext context) {
        Order order = orderService.selectById(context.getItemOrderId());
        stateHandler.fireEvent(ProductType.LINE, order.getState().getValue(), LineEvent.REFUND_APPLY, context);
    }

    @Override
    public void refundAudit(RefundAuditContext context) {
        if (context.getState() == 1) {
            stateHandler.fireEvent(ProductType.LINE, OrderState.REFUND.getValue(), LineEvent.REFUND_PASS, context);
        } else {
            stateHandler.fireEvent(ProductType.LINE, OrderState.REFUND.getValue(), LineEvent.REFUND_REFUSE, context);
        }
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.LINE, OrderState.UN_USED.getValue(), LineEvent.VERIFY, context);
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.LINE, context.getFrom(), LineEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.LINE, context.getFrom(), LineEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.LINE, context.getFrom(), LineEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.LINE, context.getFrom(), LineEvent.REFUND_FAIL, context);
    }
}
