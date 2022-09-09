package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.common.enums.ref.RefundType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.dao.model.ProductOrder;
import com.eghm.model.dto.business.order.ApplyRefundDTO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.ProductOrderService;
import com.eghm.service.business.handler.impl.DefaultApplyRefundHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/9/9
 */
@Service("productApplyRefundHandler")
@Slf4j
public class ProductApplyRefundHandler extends DefaultApplyRefundHandler {

    private final ProductOrderService productOrderService;

    public ProductApplyRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ProductOrderService productOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.productOrderService = productOrderService;
    }

    @Override
    protected OrderRefundLog doProcess(ApplyRefundDTO dto, Order order) {
        ProductOrder productOrder = productOrderService.selectByIdRequired(dto.getProductOrderId());
        if (!productOrder.getOrderNo().equals(dto.getOrderNo())) {
            throw new BusinessException("");
        }

        OrderRefundLog refundLog = DataUtil.copy(dto, OrderRefundLog.class);
        refundLog.setProductOrderId(productOrder.getId());
        LocalDateTime now = LocalDateTime.now();
        refundLog.setApplyTime(now);
        refundLog.setState(0);
        if (order.getRefundType() == RefundType.AUDIT_REFUND) {
            refundLog.setAuditState(AuditState.APPLY);
            order.setRefundState(RefundState.APPLY);
        }
        // 更新订单信息
        getOrderService().updateById(order);
        // 新增退款记录
        getOrderRefundLogService().insert(refundLog);

        productOrder.setRefundState(dto.getNum())
        return refundLog;
    }
}
