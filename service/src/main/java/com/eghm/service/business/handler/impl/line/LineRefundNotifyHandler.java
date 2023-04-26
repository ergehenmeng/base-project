package com.eghm.service.business.handler.impl.line;

import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.impl.DefaultRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineRefundNotifyHandler")
@Slf4j
public class LineRefundNotifyHandler extends DefaultRefundNotifyHandler {
    
    private final LineOrderService lineOrderService;
    
    private final LineConfigService lineConfigService;
    
    public LineRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
            AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
            LineOrderService lineOrderService, LineConfigService lineConfigService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.lineOrderService = lineOrderService;
        this.lineConfigService = lineConfigService;
    }
    
    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                LineOrder lineOrder = lineOrderService.selectByOrderNo(order.getOrderNo());
                lineConfigService.updateStock(lineOrder.getLineConfigId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("线路退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
    }
}
