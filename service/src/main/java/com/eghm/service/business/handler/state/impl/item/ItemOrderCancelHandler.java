package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/8/25
 */
@Service("itemOrderCancelHandler")
@Slf4j
public class ItemOrderCancelHandler extends AbstractOrderCancelHandler {

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    public ItemOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService) {
        super(orderService, memberCouponService);
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ItemOrder::getSkuId, ItemOrder::getNum));
        itemSkuService.updateStock(skuNumMap);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
