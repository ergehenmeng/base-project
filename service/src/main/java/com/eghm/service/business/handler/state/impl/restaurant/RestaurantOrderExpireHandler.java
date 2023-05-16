package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.RestaurantOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.service.business.RestaurantVoucherService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderExpireHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantOrderExpireHandler")
@Slf4j
public class RestaurantOrderExpireHandler extends AbstractOrderExpireHandler {

    private final RestaurantOrderService restaurantOrderService;

    private final RestaurantVoucherService restaurantVoucherService;

    public RestaurantOrderExpireHandler(OrderService orderService, UserCouponService userCouponService, RestaurantOrderService restaurantOrderService, RestaurantVoucherService restaurantVoucherService) {
        super(orderService, userCouponService);
        this.restaurantOrderService = restaurantOrderService;
        this.restaurantVoucherService = restaurantVoucherService;
    }

    @Override
    protected void after(Order order) {
        RestaurantOrder restaurantOrder = restaurantOrderService.selectByOrderNo(order.getOrderNo());
        restaurantVoucherService.updateStock(restaurantOrder.getVoucherId(), order.getNum());
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
