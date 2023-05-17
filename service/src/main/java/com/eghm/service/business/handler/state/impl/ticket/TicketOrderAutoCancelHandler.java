package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("ticketOrderAutoCancelHandler")
@Slf4j
public class TicketOrderAutoCancelHandler extends TicketOrderCancelHandler {
    public TicketOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService, TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService) {
        super(orderService, userCouponService, ticketOrderService, scenicTicketService);
    }


    @Override
    public CloseType getCloseType() {
        return CloseType.EXPIRE;
    }


    @Override
    public IEvent getEvent() {
        return TicketEvent.AUTO_CANCEL;
    }

}
