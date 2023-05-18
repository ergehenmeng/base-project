package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.RestaurantOrder;
import com.eghm.model.RestaurantVoucher;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RestaurantOrderCreateContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/24
 */
@Service("restaurantOrderCreateHandler")
@Slf4j
public class RestaurantOrderCreateQueueHandler extends RestaurantOrderCreateHandler {

    public RestaurantOrderCreateQueueHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, RestaurantVoucherService restaurantVoucherService, RestaurantOrderService restaurantOrderService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService, restaurantVoucherService, restaurantOrderService);
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(RestaurantOrderCreateContext context, RestaurantVoucher payload) {
        return false;
    }
}
