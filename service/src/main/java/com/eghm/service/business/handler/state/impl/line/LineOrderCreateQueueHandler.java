package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.LineOrderCreateContext;
import com.eghm.service.business.handler.dto.LineOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/2
 */
@Service("lineOrderCreateQueueHandler")
@Slf4j
public class LineOrderCreateQueueHandler extends LineOrderCreateHandler {

    public LineOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, TravelAgencyService travelAgencyService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineOrderSnapshotService lineOrderSnapshotService) {
        super(orderService, memberCouponService, orderVisitorService, orderMQService, lineService, travelAgencyService, lineConfigService, lineDayConfigService, lineOrderService, lineOrderSnapshotService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(LineOrderCreateContext context, LineOrderPayload payload) {
        return false;
    }
}
