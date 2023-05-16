package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayOrderAutoCancelHandler")
@Slf4j
public class HomestayOrderAutoCancelHandler extends HomestayOrderCancelHandler {

    public HomestayOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService) {
        super(orderService, userCouponService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.AUTO_CANCEL;
    }

}
