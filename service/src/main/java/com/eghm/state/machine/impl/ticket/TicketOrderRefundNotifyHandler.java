package com.eghm.state.machine.impl.ticket;

import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.VisitorState;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.pay.enums.RefundStatus;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.RefundNotifyContext;
import com.eghm.state.machine.impl.AbstractOrderRefundNotifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("ticketOrderRefundNotifyHandler")
@Slf4j
public class TicketOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final OrderMqService orderMqService;

    private final ScenicTicketService scenicTicketService;

    private final OrderVisitorService orderVisitorService;

    public TicketOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                          VerifyLogService verifyLogService, ScenicTicketService scenicTicketService, OrderVisitorService orderVisitorService,
                                          OrderMqService orderMqService, AccountService accountService) {
        super(orderService, accountService, verifyLogService, orderRefundLogService);
        this.orderMqService = orderMqService;
        this.scenicTicketService = scenicTicketService;
        this.orderVisitorService = orderVisitorService;
    }

    @Override
    protected void after(RefundNotifyContext context, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(context, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.REFUND);
            scenicTicketService.releaseStock(order.getOrderNo(), refundLog.getNum());
        }
        if (order.getState() == OrderState.COMPLETE) {
            orderMqService.sendOrderCompleteMessage(ExchangeQueue.TICKET_COMPLETE_DELAY, order.getOrderNo());
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
