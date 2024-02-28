package com.eghm.service.business.handler.state.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.RefundState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.RefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.eghm.service.pay.enums.RefundStatus.*;

/**
 * 退款异步回调
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundNotifyHandler implements RefundNotifyHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final AggregatePayService aggregatePayService;

    private final VerifyLogService verifyLogService;

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(RefundNotifyContext context) {
        Order order = orderService.selectByTradeNo(context.getTradeNo());
        OrderRefundLog refundLog = orderRefundLogService.selectByRefundNo(context.getRefundNo());

        this.before(context, order, refundLog);

        RefundStatus refundStatus = this.doProcess(context, order, refundLog);

        this.after(context, order, refundLog, refundStatus);
    }

    /**
     * 校验订单及退款信息
     *
     * @param context   流水号
     * @param order     订单信息
     * @param refundLog 退款信息
     */
    protected void before(RefundNotifyContext context, Order order, OrderRefundLog refundLog) {
        if (order == null) {
            log.error("根据支付流水号未查询到订单,不做退款处理 [{}]", context);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        if (refundLog == null) {
            log.error("根据退款流水号未查询到退款记录,不做退款处理 [{}]", context);
        }
    }

    /**
     * 退款处理
     * 1.查询在支付平台的订单退款状态
     * 2.根据结果状态 更新订单状态,退款状态及退款记录状态
     *
     * @param context   流水号
     * @param order     订单信息
     * @param refundLog 退款记录
     * @return true: 退款成功 false:不成功
     */
    protected RefundStatus doProcess(RefundNotifyContext context, Order order, OrderRefundLog refundLog) {

        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        RefundVO refund = aggregatePayService.queryRefund(tradeType, context.getTradeNo(), context.getRefundNo());

        RefundStatus state = refund.getState();
        if (state == REFUND_SUCCESS || state == SUCCESS) {
            this.refundSuccessSetState(order, refundLog);
            refundLog.setState(1);
        } else if (state == ABNORMAL || state == CLOSED) {
            this.refundFailSetState(order, refundLog);
            refundLog.setState(2);
        }
        orderRefundLogService.updateById(refundLog);
        orderService.updateById(order);
        return state;
    }

    /**
     * 退款成功后,设置订单状态(由于涉及到部分退款等特殊情况,根据各自模块来决定订单状态走向)
     *
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void refundSuccessSetState(Order order, OrderRefundLog refundLog) {
        int refundNum = orderRefundLogService.getRefundSuccessNum(order.getOrderNo(), null);
        // 退款成功的+当前退款的大于总订单数,则默认关闭
        if ((refundNum + refundLog.getNum()) >= order.getNum()) {
            order.setState(OrderState.CLOSE);
            order.setCloseTime(LocalDateTime.now());
            order.setCloseType(CloseType.REFUND);
        } else {
            // 已核销+退款成功+当前退款成功的大于总付款数量,订单可以直接变成下一个状态
            int verifiedNum = verifyLogService.getVerifiedNum(order.getOrderNo());
            if ((verifiedNum + refundNum + refundLog.getNum()) >= order.getNum()) {
                order.setCompleteTime(LocalDateTime.now());
                order.setState(OrderState.COMPLETE);
            } else {
                log.info("核销数量+退款数量小于付款数量,可能还有部分订单待核销 [{}] [{}] [{}] [{}] [{}]",
                        order.getId(), order.getState(), order.getNum(), verifiedNum, refundNum);
            }
        }
        order.setRefundState(RefundState.SUCCESS);
    }

    /**
     * 退款失败后,设置订单状态及退款状态
     *
     * @param order     订单信息
     * @param refundLog 退款记录
     */
    protected void refundFailSetState(Order order, OrderRefundLog refundLog) {
        order.setRefundState(RefundState.FAIL);
    }

    /**
     * 退款回调后置处理, 例如退款后, 例如库存退还
     *
     * @param context      流水号
     * @param order        订单信息
     * @param refundLog    退款记录
     * @param refundStatus 退款状态
     */
    protected void after(RefundNotifyContext context, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        log.info("退款异步处理结果 [{}] [{}]", order.getOrderNo(), refundStatus);
        orderService.refundSuccessUpdateFreeze(order, context.getAmount(), context.getRefundNo());
    }

}
