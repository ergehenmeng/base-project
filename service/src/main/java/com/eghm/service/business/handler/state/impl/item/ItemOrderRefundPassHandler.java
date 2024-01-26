package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundAuditHandler;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.sys.DingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/15
 */
@Service("itemRefundPassHandler")
@Slf4j
public class ItemOrderRefundPassHandler extends AbstractOrderRefundAuditHandler {

    private final ItemOrderService itemOrderService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final DingTalkService dingTalkService;

    private final MessageService messageService;

    public ItemOrderRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService, ItemGroupOrderService itemGroupOrderService, DingTalkService dingTalkService, MessageService messageService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.itemOrderService = itemOrderService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.dingTalkService = dingTalkService;
        this.messageService = messageService;
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
        if (order.getBookingNo() == null) {
            log.info("该订单非拼团订单不做额外处理 [{}]", order.getOrderNo());
            return;
        }
        if (order.getBookingState() == 1) {
            log.warn("订单[{}]已拼团成功,无需同步退款 [{}]", order.getOrderNo(), order.getBookingNo());
            return;
        }
        if (order.getBookingState() == 2) {
            log.warn("订单[{}]已拼团失败,无需同步退款 [{}]", order.getOrderNo(), order.getBookingNo());
            return;
        }
        ItemGroupOrder groupOrder = itemGroupOrderService.getGroupOrder(order.getBookingNo(), order.getOrderNo());

        if (groupOrder == null) {
            log.error("订单[{}]无拼团记录,无法同步退款 [{}]", order.getOrderNo(), order.getBookingNo());
            dingTalkService.sendMsg(String.format("订单[%s]无拼团记录,无法同步退款 [%s]", order.getOrderNo(), order.getBookingNo()));
            return;
        }
        if (groupOrder.getState() != 0) {
            log.error("订单[{}]拼单状态异常,无法同步退款 [{}]", order.getOrderNo(), order.getBookingNo());
            dingTalkService.sendMsg(String.format("订单[%s]拼单状态异常,无法同步退款 [%s]", order.getOrderNo(), order.getBookingNo()));
            return;
        }
        // 删除当前的拼团记录
        itemGroupOrderService.delete(order.getBookingNo(), order.getOrderNo());
        if (groupOrder.getStarter()) {
            // 共用取消拼团订单的延迟队列
            log.info("订单[{}]为拼团发起者,开始同步退款 [{}]", order.getOrderNo(), order.getBookingNo());
            messageService.sendDelay(ExchangeQueue.GROUP_ORDER_EXPIRE_SINGLE, order.getBookingNo(), 5);
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
