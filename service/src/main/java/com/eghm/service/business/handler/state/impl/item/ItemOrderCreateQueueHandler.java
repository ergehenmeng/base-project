package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.dto.ItemOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Service("itemOrderCreateQueueHandler")
@Slf4j
public class ItemOrderCreateQueueHandler extends ItemOrderCreateHandler {

    public ItemOrderCreateQueueHandler(ItemOrderService itemOrderService, ItemService itemService, ItemSkuService itemSkuService, OrderService orderService, OrderMQService orderMQService) {
        super(itemOrderService, itemService, itemSkuService, orderService, orderMQService);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(ItemOrderPayload payload) {
        return false;
    }
}
