package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.common.OrderMQService;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.HomestayOrderCreateContext;
import com.eghm.service.business.handler.dto.HomestayOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 民宿下单默认全部都队列模式
 *
 * @author 二哥很猛
 * @since 2022/8/22
 */
@Slf4j
@Service("homestayOrderCreateQueueHandler")
public class HomestayOrderCreateQueueHandler extends HomestayOrderCreateHandler {

    public HomestayOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService, HomestayService homestayService,
                                           HomestayRoomConfigService homestayRoomConfigService, HomestayOrderSnapshotService homestayOrderSnapshotService, RedeemCodeGrantService redeemCodeGrantService) {
        super(orderService, memberCouponService, orderVisitorService, orderMQService, homestayOrderService, homestayRoomService, homestayService, homestayRoomConfigService, homestayOrderSnapshotService, redeemCodeGrantService);
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
