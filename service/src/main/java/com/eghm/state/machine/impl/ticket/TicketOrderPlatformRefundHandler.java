package com.eghm.state.machine.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.RefundType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.TicketOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/10/12
 */
@Service("ticketOrderPlatformRefundHandler")
@Slf4j
public class TicketOrderPlatformRefundHandler extends TicketOrderRefundApplyHandler {


    public TicketOrderPlatformRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService,
                                            TicketOrderService ticketOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService, ticketOrderService);
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.PLATFORM_REFUND;
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }
}
