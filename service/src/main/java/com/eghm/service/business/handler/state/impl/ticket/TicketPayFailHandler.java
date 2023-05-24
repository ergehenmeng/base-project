package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.state.impl.AbstractPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/24
 */
@Service("ticketPayFailHandler")
public class TicketPayFailHandler extends AbstractPayFailHandler {

    public TicketPayFailHandler(OrderService orderService) {
        super(orderService);
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
