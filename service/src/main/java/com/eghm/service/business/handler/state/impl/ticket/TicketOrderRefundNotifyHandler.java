package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("ticketRefundNotifyHandler")
@Slf4j
public class TicketOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final TicketOrderService ticketOrderService;

    private final ScenicTicketService scenicTicketService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderMQService;

    public TicketOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                          AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
                                          TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService,
                                          OrderVisitorService orderVisitorService, OrderMQService orderMQService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.ticketOrderService = ticketOrderService;
        this.scenicTicketService = scenicTicketService;
        this.orderVisitorService = orderVisitorService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected void after(RefundNotifyContext context, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(context, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.REFUND);
            try {
                TicketOrder ticketOrder = ticketOrderService.getByOrderNo(order.getOrderNo());
                scenicTicketService.updateStock(ticketOrder.getScenicId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("门票退款成功,但更新库存失败 [{}] [{}] ", context, refundLog.getNum(), e);
            }
        }
        if (order.getState() == OrderState.COMPLETE) {
            orderMQService.sendOrderCompleteMessage(ExchangeQueue.TICKET_COMPLETE_DELAY, order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
