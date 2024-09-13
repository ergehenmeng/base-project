package com.eghm.state.machine.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.context.RefundApplyContext;
import com.eghm.state.machine.impl.AbstractOrderRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("lineOrderRefundApplyHandler")
@Slf4j
public class LineOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler<RefundApplyContext> {

    public LineOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                       OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
