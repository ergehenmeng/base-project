package com.eghm.service.business.handler.impl.line;

import com.eghm.dao.model.LineOrder;
import com.eghm.dao.model.Order;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderCancelHandler;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineOrderCancelHandler")
public class LineOrderCancelHandler extends DefaultOrderCancelHandler {
    
    private final LineConfigService lineConfigService;
    
    private final LineOrderService lineOrderService;
    
    public LineOrderCancelHandler(OrderService orderService, UserCouponService userCouponService,
            LineConfigService lineConfigService, LineOrderService lineOrderService) {
        super(orderService, userCouponService);
        this.lineConfigService = lineConfigService;
        this.lineOrderService = lineOrderService;
    }
    
    @Override
    protected void after(Order order) {
        LineOrder lineOrder = lineOrderService.selectByOrderNo(order.getOrderNo());
        lineConfigService.updateStock(lineOrder.getLineConfigId(), order.getNum());
    }
}
