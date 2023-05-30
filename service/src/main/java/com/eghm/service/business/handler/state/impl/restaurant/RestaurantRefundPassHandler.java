package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractRefundPassHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("restaurantAuditPassRefundHandler")
public class RestaurantRefundPassHandler extends AbstractRefundPassHandler {

    public RestaurantRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
