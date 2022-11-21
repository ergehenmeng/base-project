package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ref.ProductRefundState;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.ProductOrder;
import com.eghm.service.business.handler.dto.AuditRefundContext;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.ProductOrderService;
import com.eghm.service.business.handler.impl.DefaultAuditRefundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/15
 */
@Service("productAuditRefundHandler")
@Slf4j
public class ProductAuditRefundHandler extends DefaultAuditRefundHandler {

    private final ProductOrderService productOrderService;

    public ProductAuditRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ProductOrderService productOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.productOrderService = productOrderService;
    }

    @Override
    protected void doRefuse(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        super.doRefuse(dto, order, refundLog);
        ProductOrder productOrder = productOrderService.selectByIdRequired(refundLog.getProductOrderId());
        // 退款拒绝后,需要将商品订单的退款状态变更
        int refundNum = getOrderRefundLogService().getTotalRefundNum(order.getOrderNo(), refundLog.getProductOrderId());
        if (refundNum > 0) {
            // 审批拒绝后一定是部分退款
            productOrder.setRefundState(ProductRefundState.REBATE);
        } else {
            productOrder.setRefundState(ProductRefundState.INIT);
        }
        productOrderService.updateById(productOrder);
    }
}
