package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.TicketOrderCreateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("ticketOrderCreateQueueHandler")
@Slf4j
public class TicketOrderCreateQueueHandler extends TicketOrderCreateHandler {

    public TicketOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, TicketOrderService ticketOrderService) {
        super(orderService, memberCouponService, orderVisitorService, orderMQService, scenicTicketService, ticketOrderService);
    }

    @Override
    public boolean isHotSell(TicketOrderCreateContext context, ScenicTicket payload) {
        return false;
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.CREATE_QUEUE;
    }
}
