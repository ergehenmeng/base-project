package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("voucherOrderPaySuccessHandler")
public class VoucherOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    public VoucherOrderPaySuccessHandler(OrderService orderService, OrderVisitorService orderVisitorService) {
        super(orderService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
