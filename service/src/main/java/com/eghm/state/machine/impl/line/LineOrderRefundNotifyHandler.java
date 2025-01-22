package com.eghm.state.machine.impl.line;

import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.VisitorState;
import com.eghm.model.LineOrder;
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
@Service("lineOrderRefundNotifyHandler")
@Slf4j
public class LineOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final LineOrderService lineOrderService;

    private final LineConfigService lineConfigService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMqService orderMqService;

    public LineOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                        VerifyLogService verifyLogService, LineOrderService lineOrderService, LineConfigService lineConfigService,
                                        OrderVisitorService orderVisitorService, OrderMqService orderMqService,
                                        AccountService accountService) {
        super(orderService, accountService, orderRefundLogService, verifyLogService);
        this.lineOrderService = lineOrderService;
        this.lineConfigService = lineConfigService;
        this.orderVisitorService = orderVisitorService;
        this.orderMqService = orderMqService;
    }

    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.REFUND);
            try {
                LineOrder lineOrder = lineOrderService.getByOrderNo(order.getOrderNo());
                lineConfigService.updateStock(lineOrder.getLineConfigId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("线路退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
        if (order.getState() == OrderState.COMPLETE) {
            orderMqService.sendOrderCompleteMessage(ExchangeQueue.LINE_COMPLETE_DELAY, order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
