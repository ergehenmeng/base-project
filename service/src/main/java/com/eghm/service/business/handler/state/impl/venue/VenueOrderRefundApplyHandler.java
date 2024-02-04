package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Slf4j
@Service("voucherOrderRefundApplyHandler")
public class VenueOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final VoucherOrderService voucherOrderService;

    public VenueOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, VoucherOrderService voucherOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.voucherOrderService = voucherOrderService;
    }

    @Override
    protected int getVerifyNum(Order order) {
        VoucherOrder voucherOrder = voucherOrderService.getByOrderNo(order.getOrderNo());
        return voucherOrder.getUseNum();
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
