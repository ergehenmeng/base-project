package com.eghm.state.machine.impl.ticket;

import com.eghm.common.OrderMQService;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.TicketOrderCreateContext;
import com.eghm.state.machine.dto.TicketOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("ticketOrderCreateQueueHandler")
@Slf4j
public class TicketOrderCreateQueueHandler extends TicketOrderCreateHandler {

    public TicketOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, ScenicService scenicService
            , TicketOrderService ticketOrderService, RedeemCodeGrantService redeemCodeGrantService, TicketOrderSnapshotService ticketOrderSnapshotService) {
        super(orderService, memberCouponService, orderVisitorService, orderMQService, scenicTicketService, scenicService, ticketOrderService, redeemCodeGrantService, ticketOrderSnapshotService);
    }

    @Override
    public boolean isHotSell(TicketOrderCreateContext context, TicketOrderPayload payload) {
        return false;
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.CREATE_QUEUE;
    }
}
