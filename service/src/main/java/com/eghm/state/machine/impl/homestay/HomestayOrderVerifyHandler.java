package com.eghm.state.machine.impl.homestay;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.model.Order;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Service("homestayOrderVerifyHandler")
public class HomestayOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    public HomestayOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService, JsonService jsonService, OrderMqService orderMqService, CommonService commonService) {
        super(jsonService, orderService, commonService, verifyLogService, orderVisitorService);
        this.orderMqService = orderMqService;
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMqService.sendOrderCompleteMessage(ExchangeQueue.HOMESTAY_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
