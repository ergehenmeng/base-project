package com.eghm.state.machine.impl.venue;

import com.eghm.common.JsonService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/4
 */
@Slf4j
@Service("venueOrderVerifyHandler")
public class VenueOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final MessageService messageService;

    public VenueOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService,
                                   JsonService jsonService, MessageService messageService, CommonService commonService) {
        super(jsonService, orderService, commonService, verifyLogService, orderVisitorService);
        this.messageService = messageService;
    }

    @Override
    protected void doProcess(OrderVerifyContext context, Order order) {
        super.noVisitProcess(context, order);
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        log.info("场馆订单完成发送实时消息 [{}]", order.getOrderNo());
        messageService.send(ExchangeQueue.ORDER_COMPLETE, order.getOrderNo());
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
