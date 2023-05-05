package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AbstractAccessHandler;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
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

    public ItemAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
    }

    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.ITEM, OrderState.NONE.getValue(), ItemEvent.CREATE, context);
    }

    @Override
    public void payNotify(PayNotifyContext context) {
        boolean paySuccess = super.checkPaySuccess(context);
        if (paySuccess) {
            stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.PAY_SUCCESS, context);
        } else {
            stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.PAY_FAIL, context);
        }
    }

    @Override
    public void refundNotify(RefundNotifyContext context) {
        boolean refundSuccess = this.checkRefundSuccess(context);
        if (refundSuccess) {
            stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.REFUND_SUCCESS, context);
        } else {
            stateHandler.fireEvent(ProductType.ITEM, context.getFrom(), ItemEvent.REFUND_FAIL, context);
        }
    }
}
