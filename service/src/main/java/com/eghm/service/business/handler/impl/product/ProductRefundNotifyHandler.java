package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ref.OrderState;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
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

    private final ProductOrderService productOrderService;

    public ProductRefundNotifyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, AggregatePayService aggregatePayService, VerifyLogService verifyLogService, ProductService productService, ProductSkuService productSkuService, ProductOrderService productOrderService) {
        super(orderService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.productService = productService;
        this.productSkuService = productSkuService;
        this.productOrderService = productOrderService;
    }

    @Override
    protected void refundSuccessSetState(Order order, OrderRefundLog refundLog) {
        int successNum = getOrderRefundLogService().getRefundSuccessNum(order.getOrderNo(), refundLog.getProductOrderId());
        int productNum = productOrderService.getProductNum(order.getOrderNo());
        if (successNum  + refundLog.getNum() >= productNum) {
            order.setState(OrderState.CLOSE);
        }

    }

    @Override
    protected void refundFailSetState(Order order, OrderRefundLog refundLog) {
        super.refundFailSetState(order, refundLog);
    }
}
