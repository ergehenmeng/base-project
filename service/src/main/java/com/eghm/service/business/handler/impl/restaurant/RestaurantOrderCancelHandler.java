package com.eghm.service.business.handler.impl.restaurant;

import com.eghm.dao.model.Order;
import com.eghm.dao.model.RestaurantOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.service.business.RestaurantVoucherService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantOrderCancelHandler")
@Slf4j
public class RestaurantOrderCancelHandler extends DefaultOrderCancelHandler {

    private final RestaurantOrderService restaurantOrderService;

    private final RestaurantVoucherService restaurantVoucherService;

    public RestaurantOrderCancelHandler(OrderService orderService, UserCouponService userCouponService, RestaurantOrderService restaurantOrderService, RestaurantVoucherService restaurantVoucherService) {
        super(orderService, userCouponService);
        this.restaurantOrderService = restaurantOrderService;
        this.restaurantVoucherService = restaurantVoucherService;
    }

    @Override
    protected void after(Order order) {
        RestaurantOrder restaurantOrder = restaurantOrderService.selectByOrderNo(order.getOrderNo());
        restaurantVoucherService.updateStock(restaurantOrder.getVoucherId(), order.getNum());
    }
}
