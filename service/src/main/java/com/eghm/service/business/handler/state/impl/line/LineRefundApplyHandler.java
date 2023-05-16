package com.eghm.service.business.handler.state.impl.line;

import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.DefaultRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineApplyRefundHandler")
@Slf4j
public class LineRefundApplyHandler extends DefaultRefundApplyHandler {
    
    public LineRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                  OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }
    
}
