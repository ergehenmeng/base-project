package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("homestayOrderPaySuccessHandler")
public class HomestayOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    public HomestayOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService) {
        super(orderService, accountService, orderVisitorService);
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
