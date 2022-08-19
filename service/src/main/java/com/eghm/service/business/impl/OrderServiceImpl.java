package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.*;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.OrderMapper;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.model.dto.business.order.ticket.AuditTicketRefundRequest;
import com.eghm.service.business.*;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.common.enums.ErrorCode.TICKET_REFUND_APPLY;
import static com.eghm.common.enums.ErrorCode.TOTAL_REFUND_MAX;
import static com.eghm.service.pay.enums.RefundState.*;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Service("orderService")
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final AggregatePayService aggregatePayService;

    private final UserCouponService userCouponService;

    private final OrderRefundLogService orderRefundLogService;

    private final VerifyLogService verifyLogService;

    private final OrderVisitorService orderVisitorService;


    @Override
    public PrepayVO prepay(Long orderId, String buyerId, TradeType tradeType) {
        Order order = this.getUnPayById(orderId);
        String outTradeNo = IdWorker.getIdStr();

        PrepayDTO dto = new PrepayDTO();
        dto.setAttach(order.getOrderNo());
        dto.setDescription(order.getTitle());
        dto.setTradeType(tradeType);
        dto.setAmount(order.getPayAmount());
        dto.setOutTradeNo(outTradeNo);
        dto.setBuyerId(buyerId);
        PrepayVO vo = aggregatePayService.createPrepay(dto);
        // 支付方式先占坑
        order.setPayType(PayType.valueOf(tradeType.name()));
        order.setOutTradeNo(outTradeNo);
        orderMapper.updateById(order);
        return vo;
    }

    @Override
    public void insert(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public Order selectByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getOutTradeNo, outTradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public Order selectById(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    public void updateById(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public Order getUnPayById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            log.error("订单已被删除 [{}]", id);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        if (order.getState() != OrderState.UN_PAY) {
            log.error("订单状态不是待支付 [{}] [{}]", id, order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        return order;
    }

    @Override
    public TradeState getOrderPayState(Order order) {
        if (StrUtil.isBlank(order.getOutTradeNo())) {
            log.info("订单未生成支付流水号 [{}]", order.getId());
            return TradeState.NOT_PAY;
        }
        PayType payType = order.getPayType();
        // 订单支付方式和支付方式需要做一层转换
        TradeType tradeType = TradeType.valueOf(payType.name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        return vo.getTradeState();
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public Order closeOrder(String orderNo, CloseType closeType) {
        Order order = this.getByOrderNo(orderNo);
        if (order == null) {
            log.error("订单已被删除 [{}] [{}]", orderNo, closeType);
            return null;
        }
        if (order.getState() != OrderState.UN_PAY) {
            log.error("订单状态不是待支付, 无需处理 [{}] [{}]", orderNo, order.getState());
            return null;
        }
        this.doCloseOrder(order, closeType);
        return order;
    }

    @Override
    public void orderPaying(Long orderId, Long userId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Order::getState, OrderState.PROGRESS);
        wrapper.set(Order::getPayTime, LocalDateTime.now());
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.UN_PAY);
        wrapper.eq(Order::getUserId, userId);
        orderMapper.update(null, wrapper);
    }

    @Override
    public void orderDelete(Long orderId, Long userId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.CLOSE);
        wrapper.eq(Order::getUserId, userId);
        orderMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void orderPayNotify(String orderNo) {
        Order order = this.getByOrderNo(orderNo);
        if (order.getState() != OrderState.PROGRESS) {
            log.error("订单状态已更改,无须更新支付状态 [{}] [{}]", orderNo, order.getState());
            return;
        }
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        if (vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS) {
            order.setState(OrderState.UN_USED);
            orderMapper.updateById(order);
        } else {
            log.error("异步通知查询订单状态非支付成功 [{}] [{}] [{}]", order.getId(), order.getOutTradeNo(), vo.getTradeState());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public boolean orderRefundNotify(String outTradeNo, String outRefundNo) {
        Order order = this.selectByOutTradeNo(outTradeNo);
        if (order == null) {
            log.error("根据支付流水号未查询到门票订单,不做退款处理 [{}] [{}]", outTradeNo, outRefundNo);
            return false;
        }
        OrderRefundLog refundLog = orderRefundLogService.selectByOutRefundNo(outRefundNo);
        if (refundLog == null) {
            log.error("根据退款流水号未查询到退款记录,不做退款处理 [{}] [{}]", outTradeNo, outRefundNo);
            return false;
        }
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        RefundVO refund = aggregatePayService.queryRefund(tradeType, outTradeNo, outRefundNo);
        com.eghm.service.pay.enums.RefundState state = refund.getState();
        if (state == REFUND_SUCCESS || state == SUCCESS) {
            this.setOrderState(order);
            order.setRefundState(RefundState.SUCCESS);
            refundLog.setState(1);
        } else if (state == ABNORMAL || state == CLOSED) {
            order.setRefundState(RefundState.FAIL);
            refundLog.setState(2);
        }
        orderRefundLogService.updateById(refundLog);
        orderMapper.updateById(order);
        return state == REFUND_SUCCESS || state == SUCCESS;
    }

    @Override
    public List<String> getPayingList() {
        return orderMapper.getPayingList();
    }

    @Override
    public void auditRefund(AuditTicketRefundRequest request) {
        Order order = this.getByOrderNo(request.getOrderNo());
        if (order.getRefundState() == null) {
            log.error("该笔订单未发起退款,无法进行审核操作 [{}] [{}]", request.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.NO_REFUND_STATE);
        }
        if (order.getRefundState() != RefundState.APPLY) {
            log.error("门票退款状态已审核 无须重复操作 [{}] [{}]", order.getId(), order.getRefundState());
            throw new BusinessException(TICKET_REFUND_APPLY);
        }
        OrderRefundLog refundLog = orderRefundLogService.selectByIdRequired(request.getRefundId());
        if (refundLog.getAuditState() != AuditState.APPLY) {
            log.error("退款记录状态已更新 [{}] [{}] ", refundLog.getId(), refundLog.getAuditState());
            throw new BusinessException(TICKET_REFUND_APPLY);
        }
        int refundNum = orderRefundLogService.getTotalRefundNum(request.getOrderNo());
        if ((refundNum + refundLog.getNum()) > order.getNum()) {
            log.error("门票累计退款金额(含本次)大于总支付金额 [{}] [{}] [{}]", order.getNum(), refundNum, refundLog.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX);
        }
        refundLog.setAuditRemark(request.getAuditRemark());
        refundLog.setAuditTime(LocalDateTime.now());
        if (request.getState().intValue() == AuditState.REFUSE.getValue()) {
            order.setRefundState(RefundState.REFUSE);
            refundLog.setAuditState(AuditState.REFUSE);
            orderVisitorService.unlockVisitor(request.getOrderNo(), refundLog.getId());
        } else {
            order.setRefundState(RefundState.PROGRESS);
            refundLog.setAuditState(AuditState.PASS);
            refundLog.setState(0);
            refundLog.setRefundAmount(request.getRefundAmount());
            refundLog.setOutRefundNo(IdWorker.getIdStr());
            this.startRefund(refundLog, order);
        }
        orderMapper.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    @Override
    public void startRefund(OrderRefundLog log, Order order) {
        RefundDTO dto = new RefundDTO();
        dto.setOutRefundNo(log.getOutRefundNo());
        dto.setOutTradeNo(order.getOutTradeNo());
        dto.setReason(log.getReason());
        dto.setAmount(log.getRefundAmount());
        dto.setTradeType(TradeType.valueOf(order.getPayType().name()));
        aggregatePayService.applyRefund(dto);
    }

    @Override
    public void orderCancel(Long orderId) {
        Order order = this.getUnPayById(orderId);
        TradeState state = this.getOrderPayState(order);
        if (state != TradeState.NOT_PAY && state != TradeState.WAIT_BUYER_PAY) {
            log.error("订单已支付,无法直接关闭 [{}] [{}]", orderId, state);
            throw new BusinessException(ErrorCode.ORDER_PAID_CANCEL);
        }
        this.doCloseOrder(order, CloseType.CANCEL);
    }

    /**
     * 关闭订单
     * @param order 订单新
     * @param closeType 关闭方式
     */
    private void doCloseOrder(Order order, CloseType closeType) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(closeType);
        order.setCloseTime(LocalDateTime.now());
        orderMapper.updateById(order);
        userCouponService.releaseCoupon(order.getCouponId());
    }

    /**
     * 根据退款记录及核销记录计算订单的最终状态
     * @param order 订单信息
     */
    private void setOrderState(Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(order.getOrderNo());
        // 所有门票都已退款
        if (refundNum >= order.getNum()) {
            order.setState(OrderState.CLOSE);
        } else {
            // 已核销+退款数量大于总付款数量,订单可以直接完成
            int verifiedNum = verifyLogService.getVerifiedNum(order.getId());
            if ((verifiedNum + refundNum) >= order.getNum()) {
                // TODO 后期添加7天自动确认完成
                order.setState(OrderState.USED);
            } else {
                log.info("核销数量+退款数量小于付款数量,可能还有部分订单待核销 [{}] [{}] [{}] [{}] [{}]",
                        order.getId(), order.getState(), order.getNum(), verifiedNum, refundNum);
            }
        }
    }
}
