package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AbstractAccessHandler;
import com.eghm.service.business.handler.context.ApplyRefundContext;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
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

    public LineAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
    }
    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.LINE, OrderState.NONE.getValue(), LineEvent.CREATE, context);
    }

    @Override
    public void refundApply(ApplyRefundContext context) {

    }

    @Override
    public void refundAudit(RefundAuditContext context) {

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
