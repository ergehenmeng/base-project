package com.eghm.state.machine.impl.homestay;

import com.eghm.enums.RefundType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/10/12
 */
@Service("homestayOrderPlatformRefundHandler")
@Slf4j
public class HomestayOrderPlatformRefundHandler extends HomestayOrderRefundApplyHandler {

    public HomestayOrderPlatformRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.PLATFORM_REFUND;
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }
}
