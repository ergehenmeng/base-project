package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.service.business.VoucherService;
import com.eghm.service.business.handler.state.impl.AbstractOrderAutoCancelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("voucherOrderExpireHandler")
@Slf4j
public class VoucherOrderAutoCancelHandler extends AbstractOrderAutoCancelHandler {

    private final VoucherOrderService voucherOrderService;

    private final VoucherService voucherService;

    public VoucherOrderAutoCancelHandler(OrderService orderService, MemberCouponService memberCouponService, VoucherOrderService voucherOrderService, VoucherService voucherService) {
        super(orderService, memberCouponService);
        this.voucherOrderService = voucherOrderService;
        this.voucherService = voucherService;
    }

    @Override
    protected void after(Order order) {
        VoucherOrder voucherOrder = voucherOrderService.getByOrderNo(order.getOrderNo());
        voucherService.updateStock(voucherOrder.getVoucherId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return RestaurantEvent.AUTO_CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
