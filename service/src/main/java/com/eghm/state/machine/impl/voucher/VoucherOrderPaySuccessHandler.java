package com.eghm.state.machine.impl.voucher;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.state.machine.impl.AbstractOrderPaySuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("voucherOrderPaySuccessHandler")
public class VoucherOrderPaySuccessHandler extends AbstractOrderPaySuccessHandler {

    private final VoucherOrderService voucherOrderService;

    public VoucherOrderPaySuccessHandler(OrderService orderService, AccountService accountService, OrderVisitorService orderVisitorService, MessageService messageService, VoucherOrderService voucherOrderService) {
        super(orderService, accountService, messageService, orderVisitorService);
        this.voucherOrderService = voucherOrderService;
    }

    @Override
    protected Long getProductId(Order order) {
        return voucherOrderService.getByOrderNo(order.getOrderNo()).getVoucherId();
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
