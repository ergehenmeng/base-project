package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.VerifyLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderVerifyHandler;
import com.eghm.service.common.JsonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Service("voucherOrderVerifyHandler")
public class VenueOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMQService orderMQService;

    private final OrderService orderService;

    private final VerifyLogService verifyLogService;

    public VenueOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService,
                                   JsonService jsonService, OrderMQService orderMQService, CommonService commonService) {
        super(orderVisitorService, orderService, verifyLogService, jsonService, commonService);
        this.orderMQService = orderMQService;
        this.orderService = orderService;
        this.verifyLogService = verifyLogService;
    }

    @Override
    protected void doProcess(OrderVerifyContext context, Order order) {
        order.setState(OrderState.COMPLETE);
        order.setCompleteTime(LocalDateTime.now());
        orderService.updateById(order);

        VerifyLog verifyLog = new VerifyLog();
        verifyLog.setMerchantId(order.getMerchantId());
        verifyLog.setOrderNo(order.getOrderNo());
        verifyLog.setRemark(context.getRemark());
        verifyLog.setUserId(context.getUserId());
        verifyLog.setNum(order.getNum());
        verifyLogService.insert(verifyLog);
        context.setVerifyNum(order.getNum());
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMQService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }
}
