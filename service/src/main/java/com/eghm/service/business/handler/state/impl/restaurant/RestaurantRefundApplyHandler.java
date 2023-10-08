package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.RestaurantOrder;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.service.business.handler.state.impl.AbstractRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Slf4j
@Service("restaurantApplyRefundHandler")
public class RestaurantRefundApplyHandler extends AbstractRefundApplyHandler {

    private final RestaurantOrderService restaurantOrderService;

    public RestaurantRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService,
                                        RestaurantOrderService restaurantOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.restaurantOrderService = restaurantOrderService;
    }

    @Override
    protected int getVerifyNum(Order order) {
        RestaurantOrder restaurantOrder = restaurantOrderService.getByOrderNo(order.getOrderNo());
        return restaurantOrder.getUseNum();
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
