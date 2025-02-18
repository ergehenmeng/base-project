package com.eghm.state.machine.impl.line;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.impl.AbstractOrderRefundAuditHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/16
 */
@Service("lineOrderRefundRefuseHandler")
public class LineOrderRefundRefuseHandler extends AbstractOrderRefundAuditHandler {

    public LineOrderRefundRefuseHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService, orderRefundLogService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.REFUND_REFUSE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
