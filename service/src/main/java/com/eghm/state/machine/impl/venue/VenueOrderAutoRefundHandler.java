package com.eghm.state.machine.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.context.RefundApplyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Slf4j
@Service("venueOrderAutoRefundHandler")
public class VenueOrderAutoRefundHandler extends VenueOrderRefundApplyHandler {

    public VenueOrderAutoRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    protected void before(RefundApplyContext context, Order order) {
        super.refundStateCheck(context, order);
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.PLATFORM_REFUND;
    }

}
