package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.state.impl.AbstractPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("linePayFailHandler")
public class LinePayFailHandler extends AbstractPayFailHandler {

    public LinePayFailHandler(OrderService orderService) {
        super(orderService);
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
