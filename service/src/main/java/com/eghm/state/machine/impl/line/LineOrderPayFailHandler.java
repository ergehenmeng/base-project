package com.eghm.state.machine.impl.line;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.impl.AbstractOrderPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("lineOrderPayFailHandler")
public class LineOrderPayFailHandler extends AbstractOrderPayFailHandler {

    public LineOrderPayFailHandler(OrderService orderService) {
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
