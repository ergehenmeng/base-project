package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractRefundRefuseHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("restaurantRefundRefuseHandler")
public class RestaurantRefundRefuseHandler extends AbstractRefundRefuseHandler {

    public RestaurantRefundRefuseHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return null;
    }
}
