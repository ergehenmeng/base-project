package com.eghm.service.business.handler.access.impl;

import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AbstractAccessHandler;
import com.eghm.service.business.handler.context.*;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.StateHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/17
 */
@Service("restaurantAccessHandler")
public class RestaurantAccessHandler extends AbstractAccessHandler {

    private final StateHandler stateHandler;

    public RestaurantAccessHandler(OrderService orderService, AggregatePayService aggregatePayService, StateHandler stateHandler) {
        super(orderService, aggregatePayService);
        this.stateHandler = stateHandler;
    }

    @Override
    public void createOrder(Context context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, OrderState.NONE.getValue(), RestaurantEvent.CREATE, context);
    }

    @Override
    public void paySuccess(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, context.getFrom(), RestaurantEvent.PAY_SUCCESS, context);
    }

    @Override
    public void payFail(PayNotifyContext context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, context.getFrom(), RestaurantEvent.PAY_FAIL, context);
    }

    @Override
    public void refundSuccess(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, context.getFrom(), RestaurantEvent.REFUND_SUCCESS, context);
    }

    @Override
    public void refundFail(RefundNotifyContext context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, context.getFrom(), RestaurantEvent.REFUND_FAIL, context);
    }


    @Override
    public void refundApply(RefundApplyContext context) {

    }

    @Override
    public void refundAudit(RefundAuditContext context) {

    }

    @Override
    public void verifyOrder(OrderVerifyContext context) {
        stateHandler.fireEvent(ProductType.RESTAURANT, OrderState.UN_USED.getValue(), HomestayEvent.VERIFY, context);
    }
}
