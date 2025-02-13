package com.eghm.state.machine.impl.item;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.service.business.*;
import com.eghm.service.member.MemberAddressService;
import com.eghm.service.member.MemberService;
import com.eghm.state.machine.context.ItemOrderCreateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Service("itemOrderCreateQueueHandler")
@Slf4j
public class ItemOrderCreateQueueHandler extends ItemOrderCreateHandler {

    public ItemOrderCreateQueueHandler(ItemService itemService, JsonService jsonService, OrderService orderService, MemberService memberService, OrderMqService orderMqService, ItemSkuService itemSkuService, ItemSpecService itemSpecService, ItemStoreService itemStoreService, ItemOrderService itemOrderService, GroupBookingService groupBookingService, MemberCouponService memberCouponService, MemberAddressService memberAddressService, ItemGroupOrderService itemGroupOrderService, LimitPurchaseItemService limitPurchaseItemService) {
        super(itemService, jsonService, orderService, memberService, orderMqService, itemSkuService, itemSpecService, itemStoreService, itemOrderService, groupBookingService, memberCouponService, memberAddressService, itemGroupOrderService, limitPurchaseItemService);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(ItemOrderCreateContext context) {
        return false;
    }
}
