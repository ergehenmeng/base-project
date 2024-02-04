package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Service("voucherOrderPayFailHandler")
public class VenueOrderPayFailHandler extends AbstractOrderPayFailHandler {

    private final VenueOrderService venueOrderService;

    public VenueOrderPayFailHandler(OrderService orderService, VenueOrderService venueOrderService) {
        super(orderService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void after(PayNotifyContext context, Order order) {
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
