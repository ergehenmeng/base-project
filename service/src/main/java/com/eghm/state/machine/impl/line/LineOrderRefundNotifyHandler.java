package com.eghm.state.machine.impl.line;

import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.VisitorState;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
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

    private final OrderMqService orderMqService;

    private final LineOrderService lineOrderService;

    private final LineConfigService lineConfigService;

    private final OrderVisitorService orderVisitorService;

    public LineOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                        OrderVerifyLogService orderVerifyLogService, LineOrderService lineOrderService, LineConfigService lineConfigService,
                                        OrderVisitorService orderVisitorService, OrderMqService orderMqService,
                                        AccountService accountService, OrderVisitorRefundService orderVisitorRefundService,
                                        MemberCouponService memberCouponService) {
        super(orderService, accountService, memberCouponService, orderVerifyLogService, orderRefundLogService, orderVisitorRefundService);
        this.orderMqService = orderMqService;
        this.lineOrderService = lineOrderService;
        this.lineConfigService = lineConfigService;
        this.orderVisitorService = orderVisitorService;
    }

    @Override
    protected void postSuccess(RefundNotifyContext context, Order order, OrderRefundLog refundLog) {
        super.postSuccess(context, order, refundLog);
        orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.REFUND);
        try {
            LineOrder lineOrder = lineOrderService.getByOrderNo(order.getOrderNo());
            lineConfigService.updateStock(lineOrder.getLineConfigId(), refundLog.getNum());
        } catch (Exception e) {
            log.error("线路退款成功,但更新库存失败 [{}] [{}] ", context, refundLog.getNum(), e);
        }
    }

    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
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
