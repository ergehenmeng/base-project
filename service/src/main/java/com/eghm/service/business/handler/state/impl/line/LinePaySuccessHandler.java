package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("linePaySuccessHandler")
public class LinePaySuccessHandler extends AbstractPaySuccessHandler {

    public LinePaySuccessHandler(OrderService orderService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
