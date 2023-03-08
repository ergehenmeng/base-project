package com.eghm.service.business.handler.impl.product;

import com.eghm.model.Order;
import com.eghm.model.ItemOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("productOrderCancelHandler")
@Slf4j
public class ProductOrderCancelHandler extends DefaultOrderCancelHandler {

    private final ProductSkuService productSkuService;

    private final ItemOrderService itemOrderService;

    public ProductOrderCancelHandler(OrderService orderService, UserCouponService userCouponService, ProductSkuService productSkuService, ItemOrderService itemOrderService) {
        super(orderService, userCouponService);
        this.productSkuService = productSkuService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ItemOrder> orderList = itemOrderService.selectByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ItemOrder::getSkuId, ItemOrder::getNum));
        productSkuService.updateStock(skuNumMap);
    }
}
