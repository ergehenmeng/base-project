package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("restaurantPaySuccessHandler")
public class RestaurantPaySuccessHandler extends AbstractPaySuccessHandler {

    public RestaurantPaySuccessHandler(OrderService orderService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
