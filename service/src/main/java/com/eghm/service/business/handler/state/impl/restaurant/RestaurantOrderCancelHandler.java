package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.RestaurantOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.service.business.MealVoucherService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("restaurantOrderCancelHandler")
@Slf4j
public class RestaurantOrderCancelHandler extends AbstractOrderCancelHandler {

    private final RestaurantOrderService restaurantOrderService;

    private final MealVoucherService mealVoucherService;

    public RestaurantOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, RestaurantOrderService restaurantOrderService, MealVoucherService mealVoucherService) {
        super(orderService, memberCouponService);
        this.restaurantOrderService = restaurantOrderService;
        this.mealVoucherService = mealVoucherService;
    }

    @Override
    protected void after(Order order) {
        RestaurantOrder restaurantOrder = restaurantOrderService.getByOrderNo(order.getOrderNo());
        mealVoucherService.updateStock(restaurantOrder.getVoucherId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
