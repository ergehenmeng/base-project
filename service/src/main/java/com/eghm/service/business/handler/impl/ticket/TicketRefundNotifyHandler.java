package com.eghm.service.business.handler.impl.ticket;

import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.handler.dto.RefundNotifyDTO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.impl.DefaultRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("ticketRefundNotifyHandler")
@Slf4j
public class TicketRefundNotifyHandler extends DefaultRefundNotifyHandler {
    
    private final TicketOrderService ticketOrderService;
    
    private final ScenicTicketService scenicTicketService;
    
    public TicketRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
            AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
            TicketOrderService ticketOrderService, ScenicTicketService scenicTicketService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.ticketOrderService = ticketOrderService;
        this.scenicTicketService = scenicTicketService;
    }
    
    @Override
    protected void after(RefundNotifyDTO dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                TicketOrder ticketOrder = ticketOrderService.selectByOrderNo(order.getOrderNo());
                scenicTicketService.updateStock(ticketOrder.getScenicId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("门票退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
    }
}
