package com.eghm.state.machine.impl.venue;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
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
                                         OrderVerifyLogService orderVerifyLogService, VenueOrderService venueOrderService, AccountService accountService,
                                         OrderVisitorRefundService orderVisitorRefundService, MemberCouponService memberCouponService) {
        super(orderService, accountService, memberCouponService, orderVerifyLogService, orderRefundLogService, orderVisitorRefundService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void postSuccess(RefundNotifyContext context, Order order, OrderRefundLog refundLog) {
        super.postSuccess(context, order, refundLog);
        try {
            venueOrderService.updateStock(order.getOrderNo(), 1);
        } catch (Exception e) {
            log.error("场馆退款成功,但更新库存失败 [{}] [{}] ", context, refundLog.getNum(), e);
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
