package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/24
 */
@Service("ticketOrderPaySuccessHandler")
public class TicketOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    public TicketOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService) {
        super(orderService, accountService, orderVisitorService);
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
