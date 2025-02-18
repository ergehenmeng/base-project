package com.eghm.state.machine.access.impl;

import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.exception.BusinessException;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.*;
import org.springframework.stereotype.Service;

import static com.eghm.enums.ErrorCode.REFUND_AUDIT;

/**
 * @author wyb
 * @since 2023/5/17
 */
@Service("voucherAccessHandler")
public class VoucherAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    public VoucherAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, context.getFrom(), VoucherEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, context.getFrom(), VoucherEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, context.getFrom(), VoucherEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, context.getFrom(), VoucherEvent.REFUND_FAIL, context);
    }


    @Override
    public void refundAudit(RefundAuditContext context) {
        throw new BusinessException(REFUND_AUDIT, ProductType.VOUCHER.getName());
    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, OrderState.UN_USED.getValue(), VoucherEvent.VERIFY, context);
    }

    @Override
    public void cancel(OrderCancelContext context) {
        stateHandler.fireEvent(ProductType.VOUCHER, OrderState.UN_PAY.getValue(), VoucherEvent.CANCEL, context);
    }
}
