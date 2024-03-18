package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.RefundType;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/9
 */

@Slf4j
@Service("itemOrderAutoRefundHandler")
public class ItemOrderAutoRefundHandler extends ItemOrderRefundApplyHandler {

    public ItemOrderAutoRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService, ItemGroupOrderService itemGroupOrderService, OrderMQService orderMQService) {
        super(orderService, orderRefundLogService, orderVisitorService, itemOrderService, itemGroupOrderService, orderMQService);
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.PLATFORM_REFUND;
    }

}
