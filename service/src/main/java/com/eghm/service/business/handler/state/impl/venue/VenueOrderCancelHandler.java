package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Service("voucherOrderCancelHandler")
@Slf4j
public class VenueOrderCancelHandler extends AbstractOrderCancelHandler {

    private final VenueOrderService venueOrderService;

    public VenueOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, VenueOrderService venueOrderService) {
        super(orderService, memberCouponService);
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected void after(Order order) {
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
