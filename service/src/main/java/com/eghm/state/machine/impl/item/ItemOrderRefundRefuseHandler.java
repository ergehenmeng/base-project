package com.eghm.state.machine.impl.item;

import com.eghm.enums.ItemRefundState;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.context.RefundAuditContext;
import com.eghm.state.machine.impl.AbstractOrderRefundAuditHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("itemOrderRefundRefuseHandler")
public class ItemOrderRefundRefuseHandler extends AbstractOrderRefundAuditHandler {

    private final ItemOrderService itemOrderService;

    public ItemOrderRefundRefuseHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService) {
        super(orderService, orderVisitorService, orderRefundLogService);
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void doRefuse(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        super.doRefuse(context, order, refundLog);
        ItemOrder itemOrder = itemOrderService.selectByIdRequired(refundLog.getItemOrderId());
        itemOrder.setRefundState(ItemRefundState.INIT);
        itemOrderService.updateById(itemOrder);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.REFUND_REFUSE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
