package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ref.CloseType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.ItemOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.impl.DefaultRefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/15
 */
@Service("productRefundNotifyHandler")
@Slf4j
public class ProductRefundNotifyHandler extends DefaultRefundNotifyHandler {

    private final ProductService productService;

    private final ProductSkuService productSkuService;

    private final ItemOrderService itemOrderService;

    public ProductRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, AggregatePayService aggregatePayService, VerifyLogService verifyLogService, ProductService productService, ProductSkuService productSkuService, ItemOrderService itemOrderService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.productService = productService;
        this.productSkuService = productSkuService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void refundSuccessSetState(Order order, OrderRefundLog refundLog) {
        int successNum = getOrderRefundLogService().getRefundSuccessNum(order.getOrderNo(), refundLog.getProductOrderId());
        int productNum = itemOrderService.getProductNum(order.getOrderNo());
        if (successNum  + refundLog.getNum() >= productNum) {
            order.setState(OrderState.CLOSE);
            order.setCloseType(CloseType.REFUND);
        }
        order.setRefundState(RefundState.SUCCESS);

        ItemOrder itemOrder = itemOrderService.selectById(refundLog.getProductOrderId());
        // 退款完成库存增加
        productSkuService.updateStock(itemOrder.getSkuId(), refundLog.getNum());
        // 总销量需要减去(一般不建议减库存)
        productService.updateSaleNum(itemOrder.getItemId(), -refundLog.getNum());
    }

    @Override
    protected void refundFailSetState(Order order, OrderRefundLog refundLog) {
        super.refundFailSetState(order, refundLog);
    }
}
