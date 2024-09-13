package com.eghm.state.machine.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.context.RefundApplyContext;
import com.eghm.state.machine.impl.AbstractOrderRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/8/23
 */
@Service("homestayOrderRefundApplyHandler")
@Slf4j
public class HomestayOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler<RefundApplyContext> {

    public HomestayOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                           OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
