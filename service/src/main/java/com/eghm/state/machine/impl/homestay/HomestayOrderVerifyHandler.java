package com.eghm.state.machine.impl.homestay;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.ConfirmState;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.exception.BusinessException;
import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Slf4j
@Service("homestayOrderVerifyHandler")
public class HomestayOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    private final HomestayOrderService homestayOrderService;

    public HomestayOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, OrderVerifyLogService orderVerifyLogService,
                                      JsonService jsonService, OrderMqService orderMqService, CommonService commonService, HomestayOrderService homestayOrderService) {
        super(jsonService, orderService, commonService, orderVisitorService, orderVerifyLogService);
        this.orderMqService = orderMqService;
        this.homestayOrderService = homestayOrderService;
    }

    @Override
    protected void before(OrderVerifyContext context, Order order) {
        super.before(context, order);
        HomestayOrder homestayOrder = homestayOrderService.getByOrderNo(context.getOrderNo());
        if (homestayOrder.getConfirmState() == ConfirmState.WAIT_CONFIRM) {
            log.info("该民宿订单尚未确认 [{}]", context.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_WAIT_CONFIRM);
        }
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
