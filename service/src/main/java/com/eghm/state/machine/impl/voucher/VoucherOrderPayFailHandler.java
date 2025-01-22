package com.eghm.state.machine.impl.voucher;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.enums.ProductType;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.impl.AbstractOrderPayFailHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("voucherOrderPayFailHandler")
public class VoucherOrderPayFailHandler extends AbstractOrderPayFailHandler {

    public VoucherOrderPayFailHandler(OrderService orderService) {
        super(orderService);
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
