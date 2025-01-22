package com.eghm.state.machine.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.AuditState;
import com.eghm.enums.RefundState;
import com.eghm.enums.VisitorState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.RefundAuditContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.*;

/**
 * 默认退款拒绝
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundAuditHandler implements ActionHandler<RefundAuditContext> {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    public void doAction(RefundAuditContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        OrderRefundLog refundLog = orderRefundLogService.selectByIdRequired(context.getRefundId());
        this.before(context, order, refundLog);
        this.doProcess(context, order, refundLog);
    }

    /**
     * 退款审核主逻辑
     *
     * @param context   审核信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    private void doProcess(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        refundLog.setAuditRemark(context.getAuditRemark());
        refundLog.setAuditTime(LocalDateTime.now());
        if (context.getState() == AuditState.PASS.getValue()) {
            this.doPass(context, order, refundLog);
            this.passAfter(context, order, refundLog);
        } else {
            this.doRefuse(context, order, refundLog);
            this.refuseAfter(context, order, refundLog);
        }
    }

    /**
     * 审核通过
     * 1.更新订单
     * 2.更新退款记录
     * 3.发起退款
     *
     * @param context   审核信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void doPass(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.PROGRESS);
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setState(0);
        refundLog.setRefundAmount(context.getRefundAmount());
        refundLog.setRefundNo(order.getProductType().generateTradeNo());
        refundLog.setAuditUserId(context.getAuditUserId());
        refundLog.setAuditTime(LocalDateTime.now());
        order.setRefundAmount(order.getRefundAmount() + context.getRefundAmount());
        order.setRefundScoreAmount(order.getRefundScoreAmount() + refundLog.getScoreAmount());
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    /**
     * 退款审核拒绝
     * 1.更新订单信息
     * 2.更新退款记录
     * 3.如果有锁定用户则解锁
     *
     * @param context   退款信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void doRefuse(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.REFUSE);
        refundLog.setAuditState(AuditState.REFUSE);
        refundLog.setState(4);
        refundLog.setAuditUserId(context.getAuditUserId());
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    /**
     * 退款审核通过后置处理
     *
     * @param context   退款信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void passAfter(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        log.info("退款审核通过后置处理 [{}] [{}] [{}]", context.getAuditRemark(), order.getOrderNo(), refundLog.getId());
        orderService.startRefund(refundLog, order);
    }

    /**
     * 退款审核拒绝后置处理
     *
     * @param context   退款信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void refuseAfter(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        log.info("退款审核拒绝, 释放游客 [{}]", context.getOrderNo());
        orderVisitorService.refundVisitor(order.getOrderNo(), refundLog.getId(), VisitorState.PAID);
    }

    /**
     * 校验退款审核
     *
     * @param context   审核信息
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void before(RefundAuditContext context, Order order, OrderRefundLog refundLog) {
        if (order.getRefundState() == null) {
            log.error("该笔订单未发起退款,无法进行审核操作 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.NO_REFUND_STATE);
        }
        if (refundLog.getAuditState() != AuditState.APPLY) {
            log.error("退款记录状态已更新 [{}] [{}] ", refundLog.getId(), refundLog.getAuditState());
            throw new BusinessException(REFUND_AUDITED);
        }
        int refundAmount = order.getRefundAmount() + context.getRefundAmount();
        if (refundAmount > order.getPayAmount()) {
            throw new BusinessException(REFUND_GT_PAY);
        }
        int refundNum = orderRefundLogService.getRefundSuccessNum(context.getOrderNo(), null);
        if ((refundNum + refundLog.getNum()) > order.getNum()) {
            log.error("累计退款数量(含本次)大于总支付数量 [{}] [{}] [{}]", order.getNum(), refundNum, refundLog.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX_NUM);
        }
    }

}
