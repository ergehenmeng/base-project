package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderMQService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderVerifyHandler;
import com.eghm.service.common.JsonService;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("itemOrderVerifyHandler")
public class ItemOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMQService orderMQService;

    public ItemOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService, JsonService jsonService, OrderMQService orderMQService) {
        super(orderVisitorService, orderService, verifyLogService, jsonService);
        this.orderMQService = orderMQService;
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMQService.sendOrderCompleteMessage(ExchangeQueue.ITEM_COMPLETE, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
