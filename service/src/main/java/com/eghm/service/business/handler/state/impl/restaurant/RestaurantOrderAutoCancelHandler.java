package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.state.impl.ticket.TicketOrderAutoCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantOrderAutoCancelHandler")
@Slf4j
public class RestaurantOrderAutoCancelHandler extends RestaurantOrderCancelHandler {

    public RestaurantOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService, RestaurantOrderService restaurantOrderService, RestaurantVoucherService restaurantVoucherService) {
        super(orderService, userCouponService, restaurantOrderService, restaurantVoucherService);
    }

    @Override
    public IEvent getEvent() {
        return null;
    }


    @Override
    public CloseType getCloseType() {
        return CloseType.EXPIRE;
    }

}
