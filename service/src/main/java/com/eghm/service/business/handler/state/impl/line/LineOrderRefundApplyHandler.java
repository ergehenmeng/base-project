package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.service.sys.DingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("lineOrderRefundApplyHandler")
@Slf4j
public class LineOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    public LineOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                       OrderVisitorService orderVisitorService, DingTalkService dingTalkService) {
        super(orderService, orderRefundLogService, orderVisitorService, dingTalkService);
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
