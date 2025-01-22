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

    public ItemOrderCreateQueueHandler(ItemOrderService itemOrderService, ItemService itemService, ItemSkuService itemSkuService, ItemSpecService itemSpecService, ItemStoreService itemStoreService, OrderService orderService,
                                       OrderMqService orderMqService, MemberAddressService memberAddressService, ItemGroupOrderService itemGroupOrderService, GroupBookingService groupBookingService, LimitPurchaseItemService limitPurchaseItemService,
                                       JsonService jsonService, MemberService memberService, MemberCouponService memberCouponService) {
        super(itemOrderService, itemService, itemSkuService, itemSpecService, itemStoreService, orderService, jsonService, orderMqService, memberAddressService, itemGroupOrderService, groupBookingService, limitPurchaseItemService, memberService, memberCouponService);
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
