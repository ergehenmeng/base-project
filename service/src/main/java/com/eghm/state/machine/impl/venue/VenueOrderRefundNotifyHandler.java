package com.eghm.state.machine.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ProductType;
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
 * @since 2024/2/4
 */
@Service("venueOrderRefundNotifyHandler")
@Slf4j
public class VenueOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final VenueOrderService venueOrderService;

    public VenueOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                         VerifyLogService verifyLogService, VenueOrderService venueOrderService, AccountService accountService) {
        super(orderService, accountService, verifyLogService, orderRefundLogService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                venueOrderService.updateStock(order.getOrderNo(), 1);
            } catch (Exception e) {
                log.error("场馆退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
