package com.eghm.service.business.handler.impl.restaurant;

import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.impl.DefaultApplyRefundHandler;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantApplyRefundHandler")
public class RestaurantApplyRefundHandler extends DefaultApplyRefundHandler {

    public RestaurantApplyRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }
}
