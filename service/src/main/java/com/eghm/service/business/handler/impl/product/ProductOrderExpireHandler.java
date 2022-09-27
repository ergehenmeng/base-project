package com.eghm.service.business.handler.impl.product;

import com.eghm.model.Order;
import com.eghm.model.ProductOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ProductOrderService;
import com.eghm.service.business.ProductSkuService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderExpireHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 普通订单30分钟过期未支付
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("productOrderExpireHandler")
@Slf4j
public class ProductOrderExpireHandler extends DefaultOrderExpireHandler {

    private final ProductSkuService productSkuService;

    private final ProductOrderService productOrderService;

    public ProductOrderExpireHandler(OrderService orderService, UserCouponService userCouponService, ProductSkuService productSkuService, ProductOrderService productOrderService) {
        super(orderService, userCouponService);
        this.productSkuService = productSkuService;
        this.productOrderService = productOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ProductOrder> orderList = productOrderService.selectByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ProductOrder::getSkuId, ProductOrder::getNum));
        productSkuService.updateStock(skuNumMap);
    }
}
