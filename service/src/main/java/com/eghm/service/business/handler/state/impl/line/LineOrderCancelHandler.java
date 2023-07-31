package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineOrderCancelHandler")
public class LineOrderCancelHandler extends AbstractOrderCancelHandler {
    
    private final LineConfigService lineConfigService;
    
    private final LineOrderService lineOrderService;
    
    public LineOrderCancelHandler(OrderService orderService, MemberCouponService memberCouponService,
            LineConfigService lineConfigService, LineOrderService lineOrderService) {
        super(orderService, memberCouponService);
        this.lineConfigService = lineConfigService;
        this.lineOrderService = lineOrderService;
    }
    
    @Override
    protected void after(Order order) {
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
