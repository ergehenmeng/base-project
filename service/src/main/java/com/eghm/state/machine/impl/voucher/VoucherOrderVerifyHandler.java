package com.eghm.state.machine.impl.voucher;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("voucherOrderVerifyHandler")
public class VoucherOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    private final VoucherOrderService voucherOrderService;

    public VoucherOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, OrderVerifyLogService orderVerifyLogService,
                                     JsonService jsonService, OrderMqService orderMqService, CommonService commonService, VoucherOrderService voucherOrderService) {
        super(jsonService, orderService, commonService, orderVisitorService, orderVerifyLogService);
        this.orderMqService = orderMqService;
        this.voucherOrderService = voucherOrderService;
    }

    @Override
    protected void doProcess(OrderVerifyContext context, Order order) {
        super.noVisitProcess(context, order);
        voucherOrderService.verifyNum(context.getOrderNo(), order.getNum());
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMqService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return VoucherEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }
}
