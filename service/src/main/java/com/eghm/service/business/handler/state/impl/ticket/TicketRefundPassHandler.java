package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractRefundPassHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("ticketAuditPassRefundHandler")
public class TicketRefundPassHandler extends AbstractRefundPassHandler {
    public TicketRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.REFUND_PASS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
