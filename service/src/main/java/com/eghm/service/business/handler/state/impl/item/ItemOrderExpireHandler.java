package com.eghm.service.business.handler.state.impl.item;

import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.DefaultOrderExpireHandler;
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
@Service("itemOrderExpireHandler")
@Slf4j
public class ItemOrderExpireHandler extends DefaultOrderExpireHandler {

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    public ItemOrderExpireHandler(OrderService orderService, UserCouponService userCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService) {
        super(orderService, userCouponService);
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ItemOrder> orderList = itemOrderService.selectByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ItemOrder::getSkuId, ItemOrder::getNum));
        itemSkuService.updateStock(skuNumMap);
    }
}
