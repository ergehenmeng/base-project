package com.eghm.state.machine.impl.ticket;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("ticketOrderVerifyHandler")
public class TicketOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    public TicketOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService,
                                    JsonService jsonService, OrderMqService orderMqService, CommonService commonService) {
        super(orderVisitorService, orderService, verifyLogService, jsonService, commonService);
        this.orderMqService = orderMqService;
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMqService.sendOrderCompleteMessage(ExchangeQueue.TICKET_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
