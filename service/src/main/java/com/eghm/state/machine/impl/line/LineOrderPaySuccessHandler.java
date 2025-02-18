package com.eghm.state.machine.impl.line;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("lineOrderPaySuccessHandler")
public class LineOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    private final LineOrderService lineOrderService;

    public LineOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService, MessageService messageService, LineOrderService lineOrderService) {
        super(orderService, accountService, messageService, orderVisitorService);
        this.lineOrderService = lineOrderService;
    }

    @Override
    protected Long getProductId(Order order) {
        return lineOrderService.getByOrderNo(order.getOrderNo()).getLineId();
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
