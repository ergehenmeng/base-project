package com.eghm.state.machine.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.state.machine.impl.AbstractOrderPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Service("venueOrderPayFailHandler")
public class VenueOrderPayFailHandler extends AbstractOrderPayFailHandler {

    private final VenueOrderService venueOrderService;

    public VenueOrderPayFailHandler(OrderService orderService, VenueOrderService venueOrderService) {
        super(orderService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void after(Order order) {
        venueOrderService.updateStock(order.getOrderNo(), 1);
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
