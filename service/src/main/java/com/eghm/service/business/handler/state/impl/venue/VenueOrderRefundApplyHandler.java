package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.*;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Slf4j
@Service("venueOrderRefundApplyHandler")
public class VenueOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final OrderService orderService;

    private final VenueOrderService venueOrderService;

    public VenueOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, VenueOrderService venueOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.orderService = orderService;
        this.venueOrderService = venueOrderService;
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }

    @Override
    protected OrderRefundLog doProcess(RefundApplyContext context, Order order) {
        if (order.getPayAmount() > 0) {
            return super.doProcess(context, order);
        } else {
            log.info("订单属于零元付,不做退款处理 [{}]", order.getOrderNo());
            order.setRefundState(RefundState.SUCCESS);
            order.setRefundAmount(0);
            order.setState(OrderState.CLOSE);
            order.setCloseTime(LocalDateTime.now());
            order.setCloseType(CloseType.REFUND);
            orderService.updateById(order);
            venueOrderService.updateStock(order.getOrderNo(), 1);
            return null;
        }
    }

    @Override
    protected void after(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("场馆订单退款申请成功 [{}] [{}] [{}]", context, order.getState(), order.getRefundState());
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
