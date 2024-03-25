package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.common.OrderMQService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundNotifyHandler;
import com.eghm.pay.enums.RefundStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("ticketOrderRefundNotifyHandler")
@Slf4j
public class TicketOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final ScenicTicketService scenicTicketService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderMQService;

    public TicketOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                          VerifyLogService verifyLogService, ScenicTicketService scenicTicketService, OrderVisitorService orderVisitorService,
                                          OrderMQService orderMQService, AccountService accountService) {
        super(orderService, accountService, orderRefundLogService, verifyLogService);
        this.scenicTicketService = scenicTicketService;
        this.orderVisitorService = orderVisitorService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected void after(RefundNotifyContext context, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(context, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.REFUND);
            scenicTicketService.releaseStock(order.getOrderNo(), refundLog.getNum());
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
