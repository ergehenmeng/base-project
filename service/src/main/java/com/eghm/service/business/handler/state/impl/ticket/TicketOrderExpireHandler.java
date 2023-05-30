package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderExpireHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Service("ticketOrderExpireHandler")
@Slf4j
public class TicketOrderExpireHandler extends AbstractOrderExpireHandler {

    private final TicketOrderService ticketOrderService;

    private final ScenicTicketService scenicTicketService;

    public TicketOrderExpireHandler(OrderService orderService, UserCouponService userCouponService, TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService) {
        super(orderService, userCouponService);
        this.ticketOrderService = ticketOrderService;
        this.scenicTicketService = scenicTicketService;
    }

    @Override
    protected void after(Order order) {
        TicketOrder ticketOrder = ticketOrderService.selectByOrderNo(order.getOrderNo());
        scenicTicketService.updateStock(ticketOrder.getTicketId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.AUTO_CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
