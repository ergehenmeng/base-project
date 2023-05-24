package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/24
 */
@Service("ticketPaySuccessHandler")
public class TicketPaySuccessHandler extends AbstractPaySuccessHandler {

    public TicketPaySuccessHandler(OrderService orderService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
