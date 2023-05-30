package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.RestaurantOrder;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.service.business.RestaurantVoucherService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.state.impl.AbstractRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("restaurantRefundNotifyHandler")
@Slf4j
public class RestaurantRefundNotifyHandler extends AbstractRefundNotifyHandler {
    
    private final RestaurantVoucherService restaurantVoucherService;
    
    private final RestaurantOrderService restaurantOrderService;
    
    public RestaurantRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
            AggregatePayService aggregatePayService, VerifyLogService verifyLogService,
            RestaurantVoucherService restaurantVoucherService, RestaurantOrderService restaurantOrderService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.restaurantVoucherService = restaurantVoucherService;
        this.restaurantOrderService = restaurantOrderService;
    }
    
    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                RestaurantOrder restaurantOrder = restaurantOrderService.selectByOrderNo(order.getOrderNo());
                restaurantVoucherService.updateStock(restaurantOrder.getVoucherId(), refundLog.getNum());
            } catch (Exception e) {
                log.error("餐饮券退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }
}
