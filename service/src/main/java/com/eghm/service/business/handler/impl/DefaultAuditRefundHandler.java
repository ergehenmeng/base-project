package com.eghm.service.business.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.AuditRefundHandler;
import com.eghm.service.business.handler.dto.AuditRefundContext;
import com.eghm.utils.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.eghm.common.enums.ErrorCode.REFUND_AUDITED;
import static com.eghm.common.enums.ErrorCode.TOTAL_REFUND_MAX;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@AllArgsConstructor
@Service("defaultAuditRefundHandler")
@Slf4j
public class DefaultAuditRefundHandler implements AuditRefundHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doAction(AuditRefundContext dto) {
        Order order = orderService.getByOrderNo(dto.getOrderNo());
        OrderRefundLog refundLog = orderRefundLogService.selectByIdRequired(dto.getRefundId());

        this.before(dto, order, refundLog);

        this.doProcess(dto, order, refundLog);

        this.after(dto, order, refundLog);
    }

    /**
     * 退款审核主逻辑
     * @param dto 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    private void doProcess(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        refundLog.setAuditRemark(dto.getAuditRemark());
        refundLog.setAuditTime(LocalDateTime.now());
        if (dto.getState().intValue() == AuditState.PASS.getValue()) {
            this.doPass(dto, order, refundLog);
        } else {
            this.doRefuse(dto, order, refundLog);
        }
    }

    /**
     * 审核通过
     * 1.更新订单
     * 2.更新退款记录
     * 3.发起退款
     * @param dto 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void doPass(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.PROGRESS);
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setState(0);
        refundLog.setRefundAmount(dto.getRefundAmount());
        refundLog.setOutRefundNo(IdWorker.getIdStr());
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
        TransactionUtil.afterCommit(() -> orderService.startRefund(refundLog, order));
    }

    /**
     * 退款审核拒绝
     * 1.更新订单信息
     * 2.更新退款记录
     * 3.如果有锁定用户则解锁
     * @param dto 退款信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void doRefuse(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.REFUSE);
        refundLog.setAuditState(AuditState.REFUSE);
        orderService.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    /**
     * 审核后置处理
     * 如果审核拒绝则解锁用户
     * @param dto 退款信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    private void after(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        if (dto.getState().intValue() == AuditState.REFUSE.getValue()) {
            orderVisitorService.unlockVisitor(dto.getOrderNo(), refundLog.getId());
        }
    }

    /**
     * 校验退款审核
     * @param dto 审核信息
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void before(AuditRefundContext dto, Order order, OrderRefundLog refundLog) {
        if (order.getRefundState() == null) {
            log.error("该笔订单未发起退款,无法进行审核操作 [{}] [{}]", dto.getOrderNo(), order.getState());
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
        int refundNum = orderRefundLogService.getRefundSuccessNum(dto.getOrderNo(), null);
        if ((refundNum + refundLog.getNum()) > order.getNum()) {
            log.error("累计退款数量(含本次)大于总支付数量 [{}] [{}] [{}]", order.getNum(), refundNum, refundLog.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX);
        }
    }

    public OrderRefundLogService getOrderRefundLogService() {
        return orderRefundLogService;
    }
}
