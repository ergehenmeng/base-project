package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.dto.ext.VenuePhase;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VenueOrder;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import com.eghm.service.common.JsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Service("voucherOrderCancelHandler")
@Slf4j
public class VenueOrderCancelHandler extends AbstractOrderCancelHandler {

    private final VenueOrderService venueOrderService;

    private final VenueSitePriceService venueSitePriceService;

    private final JsonService jsonService;

    public VenueOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, VenueOrderService venueOrderService, VenueSitePriceService venueSitePriceService, JsonService jsonService) {
        super(orderService, memberCouponService);
        this.venueOrderService = venueOrderService;
        this.venueSitePriceService = venueSitePriceService;
        this.jsonService = jsonService;
    }

    @Override
    protected void after(Order order) {
        VenueOrder venueOrder = venueOrderService.getByOrderNo(order.getOrderNo());
        List<VenuePhase> phaseList = jsonService.fromJsonList(venueOrder.getTimePhase(), VenuePhase.class);
        List<Long> ids = phaseList.stream().map(VenuePhase::getId).collect(Collectors.toList());
        venueSitePriceService.updateStock(ids, 1);
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
