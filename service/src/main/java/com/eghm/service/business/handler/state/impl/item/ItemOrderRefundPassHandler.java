package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.access.impl.ItemAccessHandler;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundAuditHandler;
import com.eghm.utils.SpringContextUtil;
import com.eghm.utils.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/9/15
 */
@Service("itemOrderRefundPassHandler")
@Slf4j
public class ItemOrderRefundPassHandler extends AbstractOrderRefundAuditHandler {

    private final ItemOrderService itemOrderService;

    private final ItemGroupOrderService itemGroupOrderService;

    public ItemOrderRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService, ItemGroupOrderService itemGroupOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.itemOrderService = itemOrderService;
        this.itemGroupOrderService = itemGroupOrderService;
    }

    @Override
    protected void doRefuse(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        super.doRefuse(context, order, refundLog);
        ItemOrder itemOrder = itemOrderService.selectByIdRequired(refundLog.getItemOrderId());
        itemOrder.setRefundState(ItemRefundState.INIT);
        itemOrderService.updateById(itemOrder);
    }

    @Override
    protected void passAfter(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        itemGroupOrderService.refundGroupOrder(order);
        // 金额大于0,需要发起真实退款
        if (refundLog.getRefundAmount() > 0) {
            super.passAfter(context, order, refundLog);
        } else {
            log.error("退款金额为0, 直接模拟退款成功 [{}] [{}]", refundLog.getRefundNo(), refundLog.getRefundAmount());
            RefundNotifyContext notify = new RefundNotifyContext();
            notify.setTradeNo(order.getTradeNo());
            notify.setRefundNo(refundLog.getRefundNo());
            notify.setAmount(0);
            notify.setSuccessTime(LocalDateTime.now());
            SpringContextUtil.getBean(ItemAccessHandler.class).refundSuccess(notify);
        }
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
