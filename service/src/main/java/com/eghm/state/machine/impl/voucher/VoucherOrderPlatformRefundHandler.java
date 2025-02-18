package com.eghm.state.machine.impl.voucher;

import com.eghm.enums.RefundType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VoucherOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/10/12
 */
@Slf4j
@Service("voucherOrderPlatformRefundHandler")
public class VoucherOrderPlatformRefundHandler extends VoucherOrderRefundApplyHandler {

    public VoucherOrderPlatformRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService,
                                             VoucherOrderService voucherOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService, voucherOrderService);
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.PLATFORM_REFUND;
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }
}
