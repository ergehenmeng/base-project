package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.DefaultOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayOrderCancelHandler")
@Slf4j
public class HomestayOrderCancelHandler extends DefaultOrderCancelHandler {

    public HomestayOrderCancelHandler(OrderService orderService, UserCouponService userCouponService) {
        super(orderService, userCouponService);
    }
}
