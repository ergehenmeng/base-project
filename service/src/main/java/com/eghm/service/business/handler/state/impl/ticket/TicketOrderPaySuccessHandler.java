package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/24
 */
@Service("ticketOrderPaySuccessHandler")
public class TicketOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    private final TicketOrderService ticketOrderService;

    public TicketOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService, MessageService messageService, TicketOrderService ticketOrderService) {
        super(orderService, accountService, messageService, orderVisitorService);
        this.ticketOrderService = ticketOrderService;
    }

    @Override
    protected Long getProductId(Order order) {
        return ticketOrderService.getByOrderNo(order.getOrderNo()).getTicketId();
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
