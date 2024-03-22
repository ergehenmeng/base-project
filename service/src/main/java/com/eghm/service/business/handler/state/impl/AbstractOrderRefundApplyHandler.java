package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.*;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.RefundApplyHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;
import static com.eghm.enums.ErrorCode.TOTAL_REFUND_MAX_NUM;
import static com.eghm.enums.ref.OrderState.PARTIAL_DELIVERY;

/**
 * 退款申请默认实现
 *
 * @author 二哥很猛
 * @since 2022/8/19
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundApplyHandler implements RefundApplyHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doAction(RefundApplyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(context, order);

        OrderRefundLog refundLog = this.doProcess(context, order);

        this.after(context, order, refundLog);
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
    protected OrderRefundLog doProcess(RefundApplyContext context, Order order) {
        this.checkRefundAmount(context, order);
        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setApplyTime(LocalDateTime.now());
        refundLog.setMerchantId(order.getMerchantId());
        refundLog.setState(0);
        if (this.getRefundType(order) == RefundType.AUDIT_REFUND) {
            refundLog.setAuditState(AuditState.APPLY);
            order.setRefundState(RefundState.APPLY);
        } else {
            refundLog.setAuditState(AuditState.PASS);
            refundLog.setAuditRemark("系统自动审核");
            refundLog.setRefundNo(order.getProductType().generateTradeNo());
            order.setRefundState(RefundState.PROGRESS);
            order.setRefundAmount(order.getRefundAmount() + context.getRefundAmount());
            orderService.startRefund(refundLog, order);
        }
        orderService.updateById(order);
        orderRefundLogService.insert(refundLog);
        return refundLog;
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
     * @param refundLog 退款记录
     */
    protected void after(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("订单退款申请成功 [{}] [{}] [{}]", context, order.getState(), order.getRefundState());
        orderVisitorService.refundLock(order.getProductType(), context.getOrderNo(), refundLog.getId(), context.getVisitorIds(), VisitorState.REFUNDING);
    }

    /**
     * 校验订单信息是否满足本次退款申请
     *
     * @param context 订单申请信息
     * @param order   主订单
     */
    protected void before(RefundApplyContext context, Order order) {
        if (!context.getMemberId().equals(order.getMemberId())) {
            log.error("订单不属于当前用户,无法退款 [{}] [{}] [{}]", context.getOrderNo(), order.getMemberId(), context.getMemberId());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (this.getRefundType(order) == RefundType.NOT_SUPPORTED) {
            log.error("该订单不支持退款 [{}]", context.getOrderNo());
            throw new BusinessException(ErrorCode.REFUND_NOT_SUPPORTED);
        }

        this.checkRefund(context, order);
    }

    /**
     * 校验可退款金额
     *
     * @param context 退款信息
     * @param order   订单信息
     */
    protected void checkRefundAmount(RefundApplyContext context, Order order) {
        int totalAmount = order.getPrice() * context.getNum();
        if (totalAmount < context.getRefundAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX.getCode(), String.format(REFUND_AMOUNT_MAX.getMsg(), DecimalUtil.centToYuan(totalAmount)));
        }
    }

    /**
     * 直接退款时,需要二次校验是否满足本次退款要求
     *
     * @param context 订单申请信息
     * @param order   主订单
     */
    protected void checkRefund(RefundApplyContext context, Order order) {
        this.checkRefundState(context, order);
        this.checkOrderState(context, order);
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), null);
        int useNum = this.getVerifyNum(order);
        // 已核销+已退款+本次退款应该小于等于下单数量
        if (order.getNum() < (refundNum + useNum + context.getNum())) {
            log.error("累计退款数量(含本次)大于总支付数量 [{}] [{}] [{}] [{}] [{}]", order.getOrderNo(), order.getNum(), refundNum, context.getNum(), useNum);
            throw new BusinessException(TOTAL_REFUND_MAX_NUM);
        }
    }

    /**
     * 校验订单状态是否满足退款要求
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void checkOrderState(RefundApplyContext context, Order order) {
        if (order.getState() != OrderState.UN_USED && order.getState() != OrderState.WAIT_TAKE &&
                order.getState() != OrderState.WAIT_DELIVERY && order.getState() != PARTIAL_DELIVERY &&
                order.getState() != OrderState.WAIT_RECEIVE) {
            log.error("订单状态不是待使用或待发货, 无法退款 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.STATE_NOT_REFUND);
        }
    }

    /**
     * 校验退款状态是否满足退款要求
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void checkRefundState(RefundApplyContext context, Order order) {
        // 同一时间内不允许一个订单发起两次退款, 由于零售一个订单可能包含多个商品, 因此退款成功的商品可以继续退款
        if (order.getRefundState() != RefundState.FAIL && order.getRefundState() != RefundState.OFFLINE &&
                order.getRefundState() != RefundState.NONE && order.getRefundState() != RefundState.SUCCESS &&
                order.getRefundState() != RefundState.REFUSE) {
            log.error("订单状态退款申请中, 无法退款 [{}] [{}]", context.getOrderNo(), order.getRefundState());
            throw new BusinessException(ErrorCode.REFUND_STATE_INVALID);
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

    /**
     * 创建默认退款记录
     *
     * @param context 上下文
     * @param order 订单号
     * @return 记录
     */
    protected OrderRefundLog createDefaultLog(RefundApplyContext context, Order order) {
        OrderRefundLog refundLog = new OrderRefundLog();
        refundLog.setMemberId(order.getMemberId());
        refundLog.setMerchantId(order.getMerchantId());
        refundLog.setNum(context.getNum());
        refundLog.setRefundAmount(0);
        refundLog.setOrderNo(order.getOrderNo());
        refundLog.setApplyType(1);
        refundLog.setApplyTime(LocalDateTime.now());
        refundLog.setState(1);
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setAuditRemark("零元购自动生成");
        refundLog.setRefundNo(order.getProductType().generateTradeNo());
        orderRefundLogService.insert(refundLog);
        return refundLog;
    }
}
