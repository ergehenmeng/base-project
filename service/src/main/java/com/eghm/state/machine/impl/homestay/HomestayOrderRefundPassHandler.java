package com.eghm.state.machine.impl.homestay;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.impl.AbstractOrderRefundAuditHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("homestayOrderRefundPassHandler")
public class HomestayOrderRefundPassHandler extends AbstractOrderRefundAuditHandler {

    public HomestayOrderRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService, orderRefundLogService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.REFUND_PASS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
