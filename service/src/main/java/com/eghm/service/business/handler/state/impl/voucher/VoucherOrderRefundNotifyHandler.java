package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.VoucherOrder;
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
@Service("voucherOrderRefundNotifyHandler")
@Slf4j
public class VoucherOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final VoucherService voucherService;

    private final VoucherOrderService voucherOrderService;

    private final OrderMQService orderMQService;

    public VoucherOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                           AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
                                           VoucherService voucherService, VoucherOrderService voucherOrderService,
                                           OrderMQService orderMQService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.voucherService = voucherService;
        this.voucherOrderService = voucherOrderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                VoucherOrder voucherOrder = voucherOrderService.getByOrderNo(order.getOrderNo());
                voucherService.updateStock(voucherOrder.getVoucherId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("餐饮券退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
        if (order.getState() == OrderState.COMPLETE) {
            orderMQService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
