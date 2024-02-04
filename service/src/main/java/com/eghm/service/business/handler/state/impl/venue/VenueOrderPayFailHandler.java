package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.dto.ext.VenuePhase;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VenueOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderPayFailHandler;
import com.eghm.service.common.JsonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Service("voucherOrderPayFailHandler")
public class VenueOrderPayFailHandler extends AbstractOrderPayFailHandler {

    private final VenueOrderService venueOrderService;

    private final VenueSitePriceService venueSitePriceService;

    private final JsonService jsonService;

    public VenueOrderPayFailHandler(OrderService orderService, VenueOrderService venueOrderService, VenueSitePriceService venueSitePriceService, JsonService jsonService) {
        super(orderService);
        this.venueOrderService = venueOrderService;
        this.venueSitePriceService = venueSitePriceService;
        this.jsonService = jsonService;
    }

    @Override
    protected void after(PayNotifyContext context, Order order) {
        VenueOrder venueOrder = venueOrderService.getByOrderNo(order.getOrderNo());
        List<VenuePhase> phaseList = jsonService.fromJsonList(venueOrder.getTimePhase(), VenuePhase.class);
        List<Long> ids = phaseList.stream().map(VenuePhase::getId).collect(Collectors.toList());
        venueSitePriceService.updateStock(ids, 1);
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
