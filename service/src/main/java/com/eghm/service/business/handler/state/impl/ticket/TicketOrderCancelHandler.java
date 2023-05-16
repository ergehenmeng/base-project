package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("ticketOrderCancelHandler")
@Slf4j
public class TicketOrderCancelHandler extends AbstractOrderCancelHandler {

    private final TicketOrderService ticketOrderService;

    private final ScenicTicketService scenicTicketService;

    public TicketOrderCancelHandler(OrderService orderService, UserCouponService userCouponService, TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService) {
        super(orderService, userCouponService);
        this.ticketOrderService = ticketOrderService;
        this.scenicTicketService = scenicTicketService;
    }

    @Override
    protected void after(Order order) {
        TicketOrder ticketOrder = ticketOrderService.selectByOrderNo(order.getOrderNo());
        scenicTicketService.updateStock(ticketOrder.getTicketId(), -order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return null;
    }
}
