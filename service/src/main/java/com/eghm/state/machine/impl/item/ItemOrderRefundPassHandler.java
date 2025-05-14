package com.eghm.state.machine.impl.item;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemGroupOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.context.RefundAuditContext;
import com.eghm.state.machine.impl.AbstractOrderRefundAuditHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/15
 */
@Service("itemOrderRefundPassHandler")
@Slf4j
public class ItemOrderRefundPassHandler extends AbstractOrderRefundAuditHandler {

    private final ItemGroupOrderService itemGroupOrderService;

    public ItemOrderRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemGroupOrderService itemGroupOrderService) {
        super(orderService, orderVisitorService, orderRefundLogService);
        this.itemGroupOrderService = itemGroupOrderService;
    }

    @Override
    protected void passAfter(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        super.passAfter(context, order, refundLog);
        itemGroupOrderService.refundGroupOrder(order);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.REFUND_PASS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
