package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.ticket.TicketOrderAutoCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantOrderAutoCancelHandler")
@Slf4j
public class RestaurantOrderAutoCancelHandler extends TicketOrderAutoCancelHandler {

    public RestaurantOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService, TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService) {
        super(orderService, userCouponService, ticketOrderService, scenicTicketService);
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

}
