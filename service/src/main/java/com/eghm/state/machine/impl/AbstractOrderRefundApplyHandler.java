package com.eghm.state.machine.impl;

import com.eghm.enums.*;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.pay.enums.RefundStatus;
import com.eghm.pay.vo.RefundVO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.RefundApplyContext;
import com.eghm.state.machine.context.RefundNotifyContext;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;
import static com.eghm.enums.ErrorCode.TOTAL_REFUND_MAX_NUM;
import static com.eghm.enums.OrderState.PARTIAL_DELIVERY;

/**
 * 退款申请默认实现
 *
 * @author 二哥很猛
 * @since 2022/8/19
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundApplyHandler<T extends RefundApplyContext> implements ActionHandler<T> {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    public void doAction(T context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(context, order);
        OrderRefundLog refundLog = this.doProcess(context, order);
        this.end(context, order, refundLog);
    }

    /**
     * 执行退款操作
     * 1. 更新订单状态
     * 2. 添加退款记录
     * 3. 如果直接退款,则发起退款申请
     *
     * @param context 退款信息
     * @param order   订单信息
     * @return 退款记录
     */
    protected OrderRefundLog doProcess(T context, Order order) {
        this.checkRefundAmount(context, order);
        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setApplyTime(LocalDateTime.now());
        refundLog.setMerchantId(order.getMerchantId());
        refundLog.setState(0);
        if (this.getRefundType(order) == RefundType.AUDIT_REFUND) {
            refundLog.setAuditState(AuditState.APPLY);
            order.setRefundState(RefundState.APPLY);
            orderService.updateById(order);
            orderRefundLogService.insert(refundLog);
            return refundLog;
        }
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setAuditRemark("系统自动审核");
        refundLog.setRefundNo(order.getProductType().generateTradeNo());
        order.setRefundState(RefundState.PROGRESS);
        refundLog.setAuditTime(LocalDateTime.now());
        order.setRefundAmount(order.getRefundAmount() + context.getRefundAmount());
        orderService.updateById(order);
        orderRefundLogService.insert(refundLog);
        orderVisitorService.refundLock(order.getProductType(), context.getOrderNo(), refundLog.getId(), context.getVisitorIds(), VisitorState.REFUNDING, getSource());
        this.tryStartRefund(refundLog, order);
        return refundLog;
    }

    /**
     * 尝试发起退款, 如果是零元付,则不真实发起支付,而是模拟退款成功
     * 注意: 发起真实退款时,可能会出现异步回调提前与当前事务的提交,导致退款异步通知逻辑获取到的订单信息不准确, 需要在回调和申请加锁
     * 且此处并没有对品类做限制即所有商品都可以零元付退款
     * @param refundLog 支付流水记录
     * @param order 订单编号
     */
    protected void tryStartRefund(OrderRefundLog refundLog, Order order) {
        if (refundLog.getRefundAmount() > 0) {
            orderService.startRefund(refundLog, order);
        } else {
            log.error("退款金额为0, 直接模拟退款成功 [{}] [{}]", refundLog.getRefundNo(), refundLog.getRefundAmount());
            AbstractAccessHandler beanHandler = this.getAccessHandler();
            if (beanHandler == null) {
                log.error("退款处理类为空, 模拟退款成功失败 [{}]", order.getOrderNo());
                return;
            }
            RefundNotifyContext context = new RefundNotifyContext();
            context.setTradeNo(order.getTradeNo());
            context.setRefundNo(refundLog.getRefundNo());
            context.setFrom(order.getState().getValue());
            RefundVO result = new RefundVO();
            result.setState(RefundStatus.REFUND_SUCCESS);
            result.setAmount(0);
            result.setSuccessTime(LocalDateTime.now());
            context.setResult(result);
            beanHandler.refundSuccess(context);
        }
    }

    /**
     * 模拟退款成功的handler, 主要用于零元付
     *
     * @return null 时默认不触发退款
     */
    protected AbstractAccessHandler getAccessHandler() {
        return null;
    }

    /**
     * 获取退款方式
     *
     * @param order 订单信息
     * @return 退款方式
     */
    protected RefundType getRefundType(Order order) {
        return order.getRefundType();
    }

    /**
     * 退款申请成功的后置处理
     *
     * @param context   请求参数
     * @param order     订单信息
     */
    protected void end(T context, Order order, OrderRefundLog refundLog) {
        log.info("订单退款申请成功 [{}] [{}] [{}] [{}]", context, order.getState(), order.getRefundState(), refundLog.getId());
    }

    /**
     * 退款申请前获取退款来源状态
     * 默认只有已支付的才能支持退款
     * @return 已支付
     */
    protected VisitorState[] getSource() {
        return new VisitorState[]{ VisitorState.PAID };
    }

    /**
     * 校验订单信息是否满足本次退款申请
     *
     * @param context 订单申请信息
     * @param order   主订单
     */
    protected void before(T context, Order order) {
        if (!context.getMemberId().equals(order.getMemberId())) {
            log.error("订单不属于当前用户,无法退款 [{}] [{}] [{}]", context.getOrderNo(), order.getMemberId(), context.getMemberId());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (this.getRefundType(order) == RefundType.NOT_SUPPORTED) {
            log.error("该订单不支持退款 [{}]", context.getOrderNo());
            throw new BusinessException(ErrorCode.REFUND_NOT_SUPPORTED);
        }
        this.refundStateCheck(context, order);
        this.orderStateCheck(context, order);
        this.refundNumCheck(context, order);
    }

    /**
     * 校验可退款金额
     *
     * @param context 退款信息
     * @param order   订单信息
     */
    protected void checkRefundAmount(T context, Order order) {
        int totalAmount = order.getPrice() * context.getNum();
        if (totalAmount < context.getRefundAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX, DecimalUtil.centToYuan(totalAmount));
        }
    }

    /**
     * 校验退款状态是否满足退款要求
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void refundStateCheck(T context, Order order) {
        // 同一时间内不允许一个订单发起两次退款, 由于零售一个订单可能包含多个商品, 因此退款成功的商品可以继续退款
        if (order.getRefundState() == RefundState.PROGRESS || order.getRefundState() == RefundState.APPLY) {
            log.error("订单状态退款申请中, 无法退款 [{}] [{}]", context.getOrderNo(), order.getRefundState());
            throw new BusinessException(ErrorCode.REFUND_STATE_INVALID);
        }
    }

    /**
     * 校验订单状态是否满足退款要求
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void orderStateCheck(T context, Order order) {
        if (order.getState() != OrderState.UN_USED && order.getState() != OrderState.WAIT_TAKE &&
                order.getState() != OrderState.WAIT_DELIVERY && order.getState() != PARTIAL_DELIVERY &&
                order.getState() != OrderState.WAIT_RECEIVE) {
            log.error("订单状态不是待使用或待发货, 无法退款 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.STATE_NOT_REFUND);
        }
    }

    /**
     * 根据已退款数量判断退款是否满足
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void refundNumCheck(T context, Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), null);
        int useNum = this.getVerifyNum(order);
        // 已核销+已退款+本次退款应该小于等于下单数量
        if (order.getNum() < (refundNum + useNum + context.getNum())) {
            log.error("累计退款数量(含本次)大于总支付数量 [{}] [{}] [{}] [{}] [{}]", order.getOrderNo(), order.getNum(), refundNum, context.getNum(), useNum);
            throw new BusinessException(TOTAL_REFUND_MAX_NUM);
        }
    }

    /**
     * 查询订单已核销数量
     *
     * @param order 订单信息
     */
    protected int getVerifyNum(Order order) {
        return 0;
    }

}
