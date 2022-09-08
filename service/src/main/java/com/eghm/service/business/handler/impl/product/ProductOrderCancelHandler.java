package com.eghm.service.business.handler.impl.product;

import com.eghm.dao.model.Order;
import com.eghm.dao.model.ProductOrder;
import com.eghm.dao.model.RestaurantOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.impl.DefaultOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("productOrderCancelHandler")
@Slf4j
public class ProductOrderCancelHandler extends DefaultOrderCancelHandler {

    private final ProductSkuService productSkuService;

    private final ProductOrderService productOrderService;

    public ProductOrderCancelHandler(OrderService orderService, UserCouponService userCouponService, RestaurantOrderService restaurantOrderService, ProductSkuService productSkuService, RestaurantVoucherService restaurantVoucherService, ProductOrderService productOrderService) {
        super(orderService, userCouponService);
        this.productSkuService = productSkuService;
        this.productOrderService = productOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ProductOrder> orderList = productOrderService.selectByOrderNo(order.getOrderNo());
    }
}
