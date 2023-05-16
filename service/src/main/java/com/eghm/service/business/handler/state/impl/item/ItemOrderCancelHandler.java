package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.ItemOrder;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("itemOrderCancelHandler")
@Slf4j
public class ItemOrderCancelHandler extends AbstractOrderCancelHandler {

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    public ItemOrderCancelHandler(OrderService orderService, UserCouponService userCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService) {
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

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return null;
    }
}
