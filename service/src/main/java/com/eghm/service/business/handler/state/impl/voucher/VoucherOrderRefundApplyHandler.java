package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.service.sys.DingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/25
 */
@Slf4j
@Service("voucherOrderRefundApplyHandler")
public class VoucherOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final VoucherOrderService voucherOrderService;

    public VoucherOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService,
                                          VoucherOrderService voucherOrderService, DingTalkService dingTalkService) {
        super(orderService, orderRefundLogService, orderVisitorService, dingTalkService);
        this.voucherOrderService = voucherOrderService;
    }

    @Override
    protected int getVerifyNum(Order order) {
        VoucherOrder voucherOrder = voucherOrderService.getByOrderNo(order.getOrderNo());
        return voucherOrder.getUseNum();
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
