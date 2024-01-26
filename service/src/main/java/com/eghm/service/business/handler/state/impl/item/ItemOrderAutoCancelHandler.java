package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.state.impl.AbstractOrderAutoCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 普通订单30分钟过期未支付
 *
 * @author 二哥很猛
 * @since 2022/8/25
 */
@Service("itemOrderExpireHandler")
@Slf4j
public class ItemOrderAutoCancelHandler extends AbstractOrderAutoCancelHandler {

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    private final ItemGroupOrderService itemGroupOrderService;

    public ItemOrderAutoCancelHandler(OrderService orderService, MemberCouponService memberCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService, ItemGroupOrderService itemGroupOrderService) {
        super(orderService, memberCouponService);
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
        this.itemGroupOrderService = itemGroupOrderService;
    }

    @Override
    protected void after(Order order) {
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ItemOrder::getSkuId, ItemOrder::getNum));
        itemSkuService.updateStock(skuNumMap);
        if (order.getBookingNo() != null) {
            itemGroupOrderService.delete(order.getBookingNo(), order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.AUTO_CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
