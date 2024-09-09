package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.common.OrderMQService;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.pay.enums.RefundStatus;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundNotifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2022/9/3
 */
@Service("homestayRefundNotifyHandler")
@Slf4j
public class HomestayOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final OrderMQService orderMQService;

    public HomestayOrderRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                            VerifyLogService verifyLogService, HomestayOrderService homestayOrderService, HomestayRoomConfigService homestayRoomConfigService,
                                            OrderMQService orderMQService, AccountService accountService) {
        super(orderService, accountService, orderRefundLogService, verifyLogService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomConfigService = homestayRoomConfigService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected void after(RefundNotifyContext dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        super.after(dto, order, refundLog, refundStatus);
        if (refundStatus == RefundStatus.SUCCESS || refundStatus == RefundStatus.REFUND_SUCCESS) {
            try {
                HomestayOrder homestayOrder = homestayOrderService.getByOrderNo(order.getOrderNo());
                // 也就是说民宿订单定4天 不能住两天 然后退两天, 后期需要优化该逻辑
                homestayRoomConfigService.updateStock(homestayOrder.getRoomId(), homestayOrder.getStartDate(), homestayOrder.getEndDate(), refundLog.getNum());
            } catch (Exception e) {
                log.error("线路退款成功,但更新库存失败 [{}] [{}] ", dto, refundLog.getNum(), e);
            }
        }
        if (order.getState() == OrderState.COMPLETE) {
            orderMQService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, order.getOrderNo());
        }
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
