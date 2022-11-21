package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.ProductRefundState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.ProductOrder;
import com.eghm.service.business.handler.dto.ApplyRefundContext;
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
    protected OrderRefundLog doProcess(ApplyRefundContext dto, Order order) {
        ProductOrder productOrder = productOrderService.selectByIdRequired(dto.getProductOrderId());
        if (!productOrder.getOrderNo().equals(dto.getOrderNo())) {
            log.error("订单id与订单编号不匹配,无法申请退款 [{}] [{}]", dto.getProductOrderId(), dto.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_NOT_MATCH);
        }
        int refundNum = getOrderRefundLogService().getTotalRefundNum(dto.getOrderNo(), dto.getProductOrderId());
        if (refundNum >= productOrder.getNum()) {
            log.error("该商品已全部申请退款, 无须再次申请 [{}] [{}]", dto.getOrderNo(), dto.getProductOrderId());
            throw new BusinessException(ErrorCode.ORDER_REFUND_APPLY);
        }
        int totalRefund = dto.getNum() + refundNum;
        if (totalRefund > productOrder.getNum()) {
            log.error("商品总退款数量大于下单数量 [{}] [{}] [{}] [{}] [{}]", dto.getOrderNo(), dto.getProductOrderId(), productOrder.getNum(), dto.getNum(), refundNum);
            throw new BusinessException(ErrorCode.REFUND_MUM_MATCH);
        }
        productOrder.setRefundState(totalRefund == productOrder.getNum() ? ProductRefundState.REFUND : ProductRefundState.REBATE);

        OrderRefundLog refundLog = DataUtil.copy(dto, OrderRefundLog.class);
        refundLog.setProductOrderId(productOrder.getId());
        LocalDateTime now = LocalDateTime.now();
        refundLog.setApplyTime(now);
        refundLog.setState(0);
        refundLog.setAuditState(AuditState.APPLY);
        order.setRefundState(RefundState.APPLY);
        // 更新订单信息
        getOrderService().updateById(order);
        // 新增退款记录
        getOrderRefundLogService().insert(refundLog);
        // 更新商品订单
        productOrderService.updateById(productOrder);

        return refundLog;
    }

    @Override
    protected void after(ApplyRefundContext dto, Order order, OrderRefundLog refundLog) {
        log.info("商品订单退款申请成功 [{}] [{}] [{}]", dto.getOrderNo(), dto.getProductOrderId(), refundLog.getId());
    }
}
