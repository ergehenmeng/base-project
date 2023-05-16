package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.service.business.handler.state.impl.AbstractRefundPassHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/15
 */
@Service("itemAuditRefundHandler")
@Slf4j
public class ItemRefundPassHandler extends AbstractRefundPassHandler {

    private final ItemOrderService itemOrderService;

    public ItemRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void doRefuse(RefundAuditContext dto, Order order, OrderRefundLog refundLog) {
        super.doRefuse(dto, order, refundLog);
        ItemOrder itemOrder = itemOrderService.selectByIdRequired(refundLog.getItemOrderId());
        // 退款拒绝后,需要将商品订单的退款状态变更
        int refundNum = getOrderRefundLogService().getTotalRefundNum(order.getOrderNo(), refundLog.getItemOrderId());
        if (refundNum > 0) {
            // 审批拒绝后一定是部分退款
            itemOrder.setRefundState(ItemRefundState.REBATE);
        } else {
            itemOrder.setRefundState(ItemRefundState.INIT);
        }
        itemOrderService.updateById(itemOrder);
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
