package com.eghm.state.machine.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("homestayOrderPaySuccessHandler")
public class HomestayOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    private final HomestayOrderService homestayOrderService;

    public HomestayOrderPaySuccessHandler(OrderService orderService, AccountService accountService, MessageService messageService, OrderVisitorService orderVisitorService, HomestayOrderService homestayOrderService) {
        super(orderService, accountService, messageService, orderVisitorService);
        this.homestayOrderService = homestayOrderService;
    }

    @Override
    protected Long getProductId(Order order) {
        return homestayOrderService.getByOrderNo(order.getOrderNo()).getRoomId();
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
