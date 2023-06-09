package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.handler.state.impl.AbstractOrderAutoCancelHandler;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineOrderExpireHandler")
public class LineOrderAutoCancelHandler extends AbstractOrderAutoCancelHandler {
    
    private final LineOrderService lineOrderService;
    
    private final LineConfigService lineConfigService;
    
    public LineOrderAutoCancelHandler(OrderService orderService, MemberCouponService memberCouponService,
                                      LineOrderService lineOrderService, LineConfigService lineConfigService) {
        super(orderService, memberCouponService);
        this.lineOrderService = lineOrderService;
        this.lineConfigService = lineConfigService;
    }
    
    @Override
    protected void after(Order order) {
        LineOrder lineOrder = lineOrderService.selectByOrderNo(order.getOrderNo());
        lineConfigService.updateStock(lineOrder.getLineConfigId(), order.getNum());
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
