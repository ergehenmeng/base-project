package com.eghm.state.machine.impl.line;

import com.eghm.common.OrderMQService;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.service.business.*;
import com.eghm.state.machine.dto.LineOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/2
 */
@Service("lineOrderCreateQueueHandler")
@Slf4j
public class LineOrderCreateQueueHandler extends LineOrderCreateHandler {

    public LineOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, TravelAgencyService travelAgencyService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineOrderSnapshotService lineOrderSnapshotService, RedeemCodeGrantService redeemCodeGrantService) {
        super(orderService, memberCouponService, orderVisitorService, orderMQService, lineService, travelAgencyService, lineConfigService, lineDayConfigService, lineOrderService, lineOrderSnapshotService, redeemCodeGrantService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(LineOrderPayload payload) {
        return false;
    }
}
