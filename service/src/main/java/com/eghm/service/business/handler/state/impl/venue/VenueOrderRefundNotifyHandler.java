package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
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
                                         AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
                                         VenueOrderService venueOrderService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
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
