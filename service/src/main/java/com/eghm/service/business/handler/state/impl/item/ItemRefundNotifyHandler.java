package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundState;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.state.impl.AbstractRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/15
 */
@Service("itemRefundNotifyHandler")
@Slf4j
public class ItemRefundNotifyHandler extends AbstractRefundNotifyHandler {

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    public ItemRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, AggregatePayService aggregatePayService, VerifyLogService verifyLogService, ItemSkuService itemSkuService, ItemOrderService itemOrderService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void refundSuccessSetState(Order order, OrderRefundLog refundLog) {
        int successNum = getOrderRefundLogService().getRefundSuccessNum(order.getOrderNo(), refundLog.getItemOrderId());
        int productNum = itemOrderService.getProductNum(order.getOrderNo());
        if (successNum  + refundLog.getNum() >= productNum) {
            order.setState(OrderState.CLOSE);
            order.setCloseType(CloseType.REFUND);
        }
        order.setRefundState(RefundState.SUCCESS);

        ItemOrder itemOrder = itemOrderService.selectById(refundLog.getItemOrderId());
        // 退款完成库存增加
        itemSkuService.updateStock(itemOrder.getSkuId(), refundLog.getNum());
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
