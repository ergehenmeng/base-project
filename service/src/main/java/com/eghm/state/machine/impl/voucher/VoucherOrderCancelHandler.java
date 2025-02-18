package com.eghm.state.machine.impl.voucher;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.model.Order;
import com.eghm.model.VoucherOrder;
import com.eghm.pay.service.AggregatePayService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.service.business.VoucherService;
import com.eghm.state.machine.impl.AbstractOrderCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/25
 */
@Service("voucherOrderCancelHandler")
@Slf4j
public class VoucherOrderCancelHandler extends AbstractOrderCancelHandler {

    private final VoucherService voucherService;

    private final VoucherOrderService voucherOrderService;

    public VoucherOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService, VoucherOrderService voucherOrderService, VoucherService voucherService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
        this.voucherService = voucherService;
        this.voucherOrderService = voucherOrderService;
    }

    @Override
    protected void after(Order order) {
        super.after(order);
        VoucherOrder voucherOrder = voucherOrderService.getByOrderNo(order.getOrderNo());
        voucherService.updateStock(voucherOrder.getVoucherId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
