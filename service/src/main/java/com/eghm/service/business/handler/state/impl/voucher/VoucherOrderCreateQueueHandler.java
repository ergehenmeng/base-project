package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.VoucherOrderCreateContext;
import com.eghm.service.business.handler.dto.VoucherOrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/24
 */
@Service("voucherOrderCreateQueueHandler")
@Slf4j
public class VoucherOrderCreateQueueHandler extends VoucherOrderCreateHandler {

    public VoucherOrderCreateQueueHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, RestaurantService restaurantService, OrderMQService orderMQService, VoucherService voucherService, VoucherOrderService voucherOrderService, RedeemCodeGrantService redeemCodeGrantService) {
        super(orderService, memberCouponService, orderVisitorService, restaurantService, orderMQService, voucherService, voucherOrderService, redeemCodeGrantService);
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.CREATE_QUEUE;
    }

    @Override
    public boolean isHotSell(VoucherOrderCreateContext context, VoucherOrderPayload payload) {
        return false;
    }
}
