package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.TicketOrder;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/23
 */
@Service("ticketOrderCancelHandler")
@Slf4j
public class TicketOrderCancelHandler extends AbstractOrderCancelHandler {

    private final TicketOrderService ticketOrderService;

    private final ScenicTicketService scenicTicketService;

    public TicketOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
        this.ticketOrderService = ticketOrderService;
        this.scenicTicketService = scenicTicketService;
    }

    @Override
    protected void after(Order order) {
        super.after(order);
        TicketOrder ticketOrder = ticketOrderService.getByOrderNo(order.getOrderNo());
        scenicTicketService.updateStock(ticketOrder.getTicketId(), -order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
