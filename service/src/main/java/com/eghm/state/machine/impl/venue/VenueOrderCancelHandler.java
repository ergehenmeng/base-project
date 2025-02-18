package com.eghm.state.machine.impl.venue;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.state.machine.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Service("venueOrderCancelHandler")
@Slf4j
public class VenueOrderCancelHandler extends AbstractOrderCancelHandler {

    private final VenueOrderService venueOrderService;

    public VenueOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, VenueOrderService venueOrderService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void after(Order order) {
        super.after(order);
        venueOrderService.updateStock(order.getOrderNo(), 1);
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
