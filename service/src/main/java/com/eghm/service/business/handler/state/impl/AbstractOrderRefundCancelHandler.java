package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ref.AuditState;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.VisitorState;
import com.eghm.exception.BusinessException;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundCancelContext;
import com.eghm.service.business.handler.state.RefundCancelHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.eghm.enums.ErrorCode.*;

/**
 * 退款异步回调
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundCancelHandler implements RefundCancelHandler {

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    private final OrderRefundLogService orderRefundLogService;

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(RefundCancelContext context) {
        OrderRefundLog refundLog = this.getRefundLog(context);
        this.before(context, refundLog);
        this.doProcess(context, refundLog);
        this.next(context, refundLog);
    }

    /**
     * 退款校验
     *
     * @param context 上下文
     * @param refundLog 退款记录
     */
    protected void before(RefundCancelContext context, OrderRefundLog refundLog) {
        if (refundLog == null) {
            log.error("退款记录不存在,订单号:[{}]", context.getOrderNo());
            throw new BusinessException(REFUND_LOG_NULL);
        }
        if (refundLog.getAuditState() == AuditState.CANCEL || refundLog.getAuditState() == AuditState.REFUSE) {
            log.error("退款已取消,无法取消,订单号:[{}]", context.getOrderNo());
            throw new BusinessException(REFUND_LOG_CANCEL);
        }
        if (refundLog.getAuditState() == AuditState.PASS) {
            log.error("退款审核通过,无法取消,订单号:[{}]", context.getOrderNo());
            throw new BusinessException(REFUND_LOG_AUDIT);
        }
    }

    /**
     * 处理退款取消
     *
     * @param context 上下文
     * @param refundLog 退款记录
     */
    protected void doProcess(RefundCancelContext context, OrderRefundLog refundLog) {
        refundLog.setState(3);
        refundLog.setAuditState(AuditState.CANCEL);
        refundLog.setOrderNo(context.getOrderNo());
        orderRefundLogService.updateById(refundLog);
    }

    /**
     * 后置处理
     *
     * @param context 上下文
     * @param refundLog 退款记录
     */
    protected void next(RefundCancelContext context, OrderRefundLog refundLog) {
        orderVisitorService.refundVisitor(context.getOrderNo(), refundLog.getId(), VisitorState.PAID);
        OrderState orderState = orderVisitorService.getOrderState(context.getOrderNo());
        orderService.updateState(context.getOrderNo(), null, orderState);
    }

    /**
     * 查询退款流水
     *
     * @param context 退款
     * @return 退款记录
     */
    protected OrderRefundLog getRefundLog(RefundCancelContext context) {
        return orderRefundLogService.getVisitRefundLog(context.getOrderNo(), context.getVisitorId());
    }

}
