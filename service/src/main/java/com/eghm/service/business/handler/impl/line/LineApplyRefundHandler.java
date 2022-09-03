package com.eghm.service.business.handler.impl.line;

import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.impl.DefaultApplyRefundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineApplyRefundHandler")
@Slf4j
public class LineApplyRefundHandler extends DefaultApplyRefundHandler {
    
    public LineApplyRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
            OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }
    
}
