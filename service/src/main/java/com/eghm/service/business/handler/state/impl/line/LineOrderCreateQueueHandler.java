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
 * @date 2022/9/2
 */
@Service("lineOrderCreateQueueHandler")
@Slf4j
public class LineOrderCreateQueueHandler extends LineOrderCreateHandler {

    public LineOrderCreateQueueHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineDaySnapshotService lineDaySnapshotService) {
        super(orderService, userCouponService, orderVisitorService, orderMQService, lineService, lineConfigService, lineDayConfigService, lineOrderService, lineDaySnapshotService);
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
