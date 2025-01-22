package com.eghm.state.machine.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ProductType;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/23
 */
@Service("homestayOrderCancelHandler")
@Slf4j
public class HomestayOrderCancelHandler extends AbstractOrderCancelHandler {

    public HomestayOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
