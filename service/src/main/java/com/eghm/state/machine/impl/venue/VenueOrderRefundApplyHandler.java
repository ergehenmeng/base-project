package com.eghm.state.machine.impl.venue;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ProductType;
import com.eghm.enums.RefundType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.access.impl.VenueAccessHandler;
import com.eghm.state.machine.context.RefundApplyContext;
import com.eghm.state.machine.impl.AbstractOrderRefundApplyHandler;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Slf4j
@Service("venueOrderRefundApplyHandler")
public class VenueOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler<RefundApplyContext> {

    public VenueOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    protected RefundType getRefundType(Order order) {
        return RefundType.DIRECT_REFUND;
    }

    @Override
    protected void end(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("场馆订单退款申请成功 [{}] [{}] [{}]", context, order.getState(), order.getRefundState());
    }

    @Override
    protected AbstractAccessHandler getAccessHandler() {
        return SpringContextUtil.getBean(VenueAccessHandler.class);
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
