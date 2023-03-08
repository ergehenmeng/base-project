package com.eghm.service.business.handler.impl.item;

import com.eghm.common.enums.ref.CloseType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.impl.DefaultRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/15
 */
@Service("itemRefundNotifyHandler")
@Slf4j
public class ItemRefundNotifyHandler extends DefaultRefundNotifyHandler {

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
    protected void refundFailSetState(Order order, OrderRefundLog refundLog) {
        super.refundFailSetState(order, refundLog);
    }
}
