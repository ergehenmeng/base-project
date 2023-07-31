package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderAutoCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayOrderExpireHandler")
@Slf4j
public class HomestayOrderAutoCancelHandler extends AbstractOrderAutoCancelHandler {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    public HomestayOrderAutoCancelHandler(OrderService orderService, MemberCouponService memberCouponService, HomestayOrderService homestayOrderService, HomestayRoomConfigService homestayRoomConfigService) {
        super(orderService, memberCouponService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomConfigService = homestayRoomConfigService;
    }

    @Override
    protected void after(Order order) {
        HomestayOrder ticketOrder = homestayOrderService.getByOrderNo(order.getOrderNo());
        homestayRoomConfigService.updateStock(ticketOrder.getRoomId(), ticketOrder.getStartDate(), ticketOrder.getEndDate(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.AUTO_CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
