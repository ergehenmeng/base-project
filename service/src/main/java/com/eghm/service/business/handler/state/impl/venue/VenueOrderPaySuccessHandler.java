package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Service("venueOrderPaySuccessHandler")
public class VenueOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    public VenueOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService) {
        super(orderService, accountService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
