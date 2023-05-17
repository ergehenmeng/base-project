package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineOrderAutoCancelHandler")
public class LineOrderAutoCancelHandler extends LineOrderCancelHandler {

    public LineOrderAutoCancelHandler(OrderService orderService, UserCouponService userCouponService, LineConfigService lineConfigService, LineOrderService lineOrderService) {
        super(orderService, userCouponService, lineConfigService, lineOrderService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.AUTO_CANCEL;
    }

    @Override
    public CloseType getCloseType() {
        return CloseType.EXPIRE;
    }
}
