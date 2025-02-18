package com.eghm.state.machine.access.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.exception.BusinessException;
import com.eghm.pay.service.AggregatePayService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.*;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/17
 */
@Service("venueAccessHandler")
public class VenueAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    public VenueAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.VENUE, context.getFrom(), VenueEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.VENUE, context.getFrom(), VenueEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.VENUE, context.getFrom(), VenueEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.VENUE, context.getFrom(), VenueEvent.REFUND_FAIL, context);
    }

    @Override
    public void refundAudit(RefundAuditContext context) {
        throw new BusinessException(ErrorCode.REFUND_AUDIT);
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.VENUE, OrderState.UN_USED.getValue(), VenueEvent.VERIFY, context);
    }

    @Override
    public void cancel(OrderCancelContext context) {
        stateHandler.fireEvent(ProductType.VENUE, OrderState.UN_PAY.getValue(), VenueEvent.CANCEL, context);
    }
}
