package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.state.impl.AbstractPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("restaurantPayFailHandler")
public class RestaurantPayFailHandler extends AbstractPayFailHandler {

    public RestaurantPayFailHandler(OrderService orderService) {
        super(orderService);
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
