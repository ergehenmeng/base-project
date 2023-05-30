package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractRefundAuditHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("homestayRefundPassHandler")
public class HomestayRefundPassHandler extends AbstractRefundAuditHandler {

    public HomestayRefundPassHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
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
