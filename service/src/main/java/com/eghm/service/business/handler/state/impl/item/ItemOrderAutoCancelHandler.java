package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("itemOrderAutoCancelHandler")
@Slf4j
public class ItemOrderAutoCancelHandler extends ItemOrderCancelHandler {

    public ItemOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService) {
        super(orderService, userCouponService, itemSkuService, itemOrderService);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.AUTO_CANCEL;
    }

    @Override
    public CloseType getCloseType() {
        return CloseType.EXPIRE;
    }
}
