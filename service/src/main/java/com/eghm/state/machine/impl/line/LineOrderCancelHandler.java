package com.eghm.state.machine.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ProductType;
import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.impl.AbstractOrderCancelHandler;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("lineOrderCancelHandler")
public class LineOrderCancelHandler extends AbstractOrderCancelHandler {

    private final LineOrderService lineOrderService;

    private final LineConfigService lineConfigService;

    public LineOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService,
                                  LineConfigService lineConfigService, LineOrderService lineOrderService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
        this.lineConfigService = lineConfigService;
        this.lineOrderService = lineOrderService;
    }

    @Override
    protected void after(Order order) {
        super.after(order);
        LineOrder lineOrder = lineOrderService.getByOrderNo(order.getOrderNo());
        lineConfigService.updateStock(lineOrder.getLineConfigId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
