package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.HomestayOrderCreateContext;
import com.eghm.service.business.handler.dto.HomestayOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/22
 */
@Service("homestayOrderCreateHandler")
@Slf4j
public class HomestayOrderCreateQueueHandler extends HomestayOrderCreateHandler {

    public HomestayOrderCreateQueueHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService, HomestayRoomConfigService homestayRoomConfigService, HomestayOrderSnapshotService homestayOrderSnapshotService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService, homestayOrderService, homestayRoomService, homestayRoomConfigService, homestayOrderSnapshotService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(HomestayOrderCreateContext context, HomestayOrderPayload payload) {
        return false;
    }
}
