package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.AuditState;
import com.eghm.enums.ref.RefundState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.service.business.handler.state.RefundAuditHandler;
import com.eghm.utils.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.REFUND_AUDITED;
import static com.eghm.enums.ErrorCode.TOTAL_REFUND_MAX;

/**
 * 默认退款拒绝
 * @author 二哥很猛
 * @date 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractRefundAuditHandler implements RefundAuditHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doAction(RefundAuditContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        OrderRefundLog refundLog = orderRefundLogService.selectByIdRequired(context.getRefundId());

        this.before(context, order, refundLog);

        this.doProcess(context, order, refundLog);

        this.after(context, order, refundLog);
    }

    /**
     * 退款审核主逻辑
     * @param context 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    private void doProcess(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        refundLog.setAuditRemark(context.getAuditRemark());
        refundLog.setAuditTime(LocalDateTime.now());
        if (context.getState() == AuditState.PASS.getValue()) {
            this.doPass(context, order, refundLog);
        } else {
            this.doRefuse(context, order, refundLog);
        }
    }

    /**
     * 审核通过
     * 1.更新订单
     * 2.更新退款记录
     * 3.发起退款
     * @param context 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void doPass(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.PROGRESS);
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setState(0);
        refundLog.setRefundAmount(context.getRefundAmount());
        refundLog.setOutRefundNo(order.getProductType().generateTradeNo());
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
        TransactionUtil.afterCommit(() -> orderService.startRefund(refundLog, order));
    }

    /**
     * 退款审核拒绝
     * 1.更新订单信息
     * 2.更新退款记录
     * 3.如果有锁定用户则解锁
     * @param context 退款信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void doRefuse(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.REFUSE);
        refundLog.setAuditState(AuditState.REFUSE);
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    /**
     * 审核后置处理
     * 如果审核拒绝则解锁用户
     * @param context 退款信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    private void after(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        if (context.getState() == AuditState.REFUSE.getValue()) {
            orderVisitorService.unlockVisitor(order.getOrderNo(), refundLog.getId());
        }
    }

    /**
     * 校验退款审核
     * @param context 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void before(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        if (order.getRefundState() == null) {
            log.error("该笔订单未发起退款,无法进行审核操作 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.NO_REFUND_STATE);
        }
        if (order.getRefundState() != RefundState.APPLY) {
            log.error("退款状态已审核 无须重复操作 [{}] [{}]", order.getId(), order.getRefundState());
            throw new BusinessException(REFUND_AUDITED);
        }
        if (refundLog.getAuditState() != AuditState.APPLY) {
            log.error("退款记录状态已更新 [{}] [{}] ", refundLog.getId(), refundLog.getAuditState());
            throw new BusinessException(REFUND_AUDITED);
        }
        int refundNum = orderRefundLogService.getRefundSuccessNum(context.getOrderNo(), null);
        if ((refundNum + refundLog.getNum()) > order.getNum()) {
            log.error("累计退款数量(含本次)大于总支付数量 [{}] [{}] [{}]", order.getNum(), refundNum, refundLog.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX);
        }
    }

}
