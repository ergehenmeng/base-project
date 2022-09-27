package com.eghm.service.business.handler.impl.line;

import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.impl.DefaultOrderExpireHandler;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineOrderExpireHandler")
public class LineOrderExpireHandler extends DefaultOrderExpireHandler {
    
    private final LineOrderService lineOrderService;
    
    private final LineConfigService lineConfigService;
    
    public LineOrderExpireHandler(OrderService orderService, UserCouponService userCouponService,
            LineOrderService lineOrderService, LineConfigService lineConfigService) {
        super(orderService, userCouponService);
        this.lineOrderService = lineOrderService;
        this.lineConfigService = lineConfigService;
    }
    
    @Override
    protected void after(Order order) {
        LineOrder lineOrder = lineOrderService.selectByOrderNo(order.getOrderNo());
        lineConfigService.updateStock(lineOrder.getLineConfigId(), order.getNum());
    }
}
