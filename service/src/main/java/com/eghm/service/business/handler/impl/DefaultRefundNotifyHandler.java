package com.eghm.service.business.handler.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.model.dto.business.order.RefundNotifyDTO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.RefundNotifyHandler;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.RefundStatus;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.eghm.service.pay.enums.RefundStatus.*;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@AllArgsConstructor
@Service("defaultRefundNotifyHandler")
@Slf4j
public class DefaultRefundNotifyHandler implements RefundNotifyHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final AggregatePayService aggregatePayService;

    private final VerifyLogService verifyLogService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Async
    public void process(RefundNotifyDTO dto) {
        Order order = orderService.selectByOutTradeNo(dto.getOutTradeNo());
        OrderRefundLog refundLog = orderRefundLogService.selectByOutRefundNo(dto.getOutRefundNo());

        this.before(dto, order, refundLog);

        RefundStatus refundStatus = this.doProcess(dto, order, refundLog);

        this.after(dto, order, refundLog, refundStatus);
    }

    /**
     * 校验订单及退款信息
     * @param dto 流水号
     * @param order 订单信息
     * @param refundLog 退款信息
     */
    protected void before(RefundNotifyDTO dto, Order order, OrderRefundLog refundLog) {
        if (order == null) {
            log.error("根据支付流水号未查询到订单,不做退款处理 [{}]", dto);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        if (refundLog == null) {
            log.error("根据退款流水号未查询到退款记录,不做退款处理 [{}]", dto);
        }
    }

    /**
     * 退款处理
     * 1.查询在支付平台的订单退款状态
     * 2.根据结果状态 更新订单状态,退款状态及退款记录状态
     *
     * @param dto 流水号
     * @param order 订单信息
     * @param refundLog 退款记录
     * @return true: 退款成功 false:不成功
     */
    private RefundStatus doProcess(RefundNotifyDTO dto, Order order, OrderRefundLog refundLog) {

        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        RefundVO refund = aggregatePayService.queryRefund(tradeType, dto.getOutTradeNo(), dto.getOutRefundNo());

        RefundStatus state = refund.getState();
        if (state == REFUND_SUCCESS || state == SUCCESS) {
            this.refundSuccessOrderState(order);
            order.setRefundState(RefundState.SUCCESS);
            refundLog.setState(1);
        } else if (state == ABNORMAL || state == CLOSED) {
            order.setRefundState(RefundState.FAIL);
            refundLog.setState(2);
        }
        orderRefundLogService.updateById(refundLog);
        orderService.updateById(order);

        return state;
    }

    /**
     * 退款成功后,设置订单状态(由于涉及到部分退款等特殊情况,根据各自模块来决定订单状态走向)
     * @param order 订单信息
     */
    protected void refundSuccessOrderState(Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(order.getOrderNo());
        // 所有都已退款
        if (refundNum >= order.getNum()) {
            order.setState(OrderState.CLOSE);
        } else {
            // 已核销+退款数量大于总付款数量,订单可以直接完成
            int verifiedNum = verifyLogService.getVerifiedNum(order.getId());
            if ((verifiedNum + refundNum) >= order.getNum()) {
                order.setState(OrderState.USED);
            } else {
                log.info("核销数量+退款数量小于付款数量,可能还有部分订单待核销 [{}] [{}] [{}] [{}] [{}]",
                        order.getId(), order.getState(), order.getNum(), verifiedNum, refundNum);
            }
        }
    }

    /**
     * 退款回调后置处理, 例如退款后, 例如库存退还
     * @param dto 流水号
     * @param order 订单信息
     * @param refundLog 退款记录
     * @param refundStatus 退款状态
     */
    protected void after(RefundNotifyDTO dto, Order order, OrderRefundLog refundLog, RefundStatus refundStatus) {
        log.info("退款处理结果 [{}] [{}]", order.getOrderNo(), refundStatus);
    }
}
