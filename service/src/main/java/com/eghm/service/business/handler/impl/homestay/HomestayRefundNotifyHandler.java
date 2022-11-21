package com.eghm.service.business.handler.impl.homestay;

import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.handler.dto.RefundNotifyDTO;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.HomestayRoomConfigService;
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
@Service("homestayRefundNotifyHandler")
@Slf4j
public class HomestayRefundNotifyHandler extends DefaultRefundNotifyHandler {
    
    private final HomestayOrderService homestayOrderService;
    
    private final HomestayRoomConfigService homestayRoomConfigService;
    
    public HomestayRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
            AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
            HomestayOrderService homestayOrderService, HomestayRoomConfigService homestayRoomConfigService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomConfigService = homestayRoomConfigService;
    }
    
    @Override
    protected void after(RefundNotifyDTO dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                HomestayOrder homestayOrder = homestayOrderService.selectByOrderNo(order.getOrderNo());
                // 也就是说民宿订单定4天 不能住两天 然后退两天, 后期需要优化该逻辑
                homestayRoomConfigService.updateStock(homestayOrder.getRoomId(), homestayOrder.getStartDate(), homestayOrder.getEndDate(), refundLog.getNum());
            } catch (Exception e) {
                log.error("线路退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
    }
}
