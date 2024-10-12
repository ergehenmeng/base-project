package com.eghm.state.machine.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.RefundType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2024/10/12
 */
@Service("lineOrderPlatformRefundHandler")
@Slf4j
public class LineOrderPlatformRefundHandler extends LineOrderRefundApplyHandler {

    public LineOrderPlatformRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                          OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.PLATFORM_REFUND;
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }
}
