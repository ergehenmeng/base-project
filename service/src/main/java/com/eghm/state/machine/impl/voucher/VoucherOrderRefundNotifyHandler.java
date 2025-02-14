package com.eghm.state.machine.impl.voucher;

import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.VoucherOrder;
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
@Service("voucherOrderRefundNotifyHandler")
@Slf4j
public class VoucherOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final OrderMqService orderMqService;

    private final VoucherService voucherService;

    private final VoucherOrderService voucherOrderService;

    public VoucherOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                           VerifyLogService verifyLogService, VoucherService voucherService, VoucherOrderService voucherOrderService,
                                           OrderMqService orderMqService, AccountService accountService) {
        super(orderService, accountService, verifyLogService, orderRefundLogService);
        this.voucherService = voucherService;
        this.orderMqService = orderMqService;
        this.voucherOrderService = voucherOrderService;
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
            orderMqService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
