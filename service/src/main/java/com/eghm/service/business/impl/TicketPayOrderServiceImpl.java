package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.*;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.TicketOrderMapper;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.ApplyTicketRefundDTO;
import com.eghm.model.dto.business.order.ticket.AuditTicketRefundRequest;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;
import com.eghm.service.business.*;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import com.eghm.utils.DataUtil;
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
 * @date 2022/7/12
 */
@Service("ticketPayOrderService")
@AllArgsConstructor
@Slf4j
public class TicketPayOrderServiceImpl implements TicketOrderService, PayOrderService {

    private final TicketOrderMapper ticketOrderMapper;

    private final ScenicTicketService scenicTicketService;

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderHandlerService;

    private final AggregatePayService aggregatePayService;

    private final OrderRefundLogService orderRefundLogService;

    private final VerifyLogService verifyLogService;

    @Override
    public void create(CreateTicketOrderDTO dto) {
        ScenicTicket ticket = scenicTicketService.selectByIdShelve(dto.getTicketId());
        this.checkTicket(ticket, dto);

        TicketOrder order = DataUtil.copy(ticket, TicketOrder.class);
        order.setOrderNo(ProductType.TICKET.getPrefix() + IdWorker.getIdStr());
        order.setMobile(dto.getMobile());
        order.setTicketId(dto.getTicketId());
        order.setUserId(dto.getUserId());
        order.setPayAmount(dto.getNum() * ticket.getSalePrice());

        if (dto.getCouponId() != null) {
            Integer couponAmount = userCouponService.getCouponAmountWithVerify(dto.getUserId(), dto.getCouponId(), dto.getTicketId(), order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setCouponId(dto.getCouponId());
            userCouponService.useCoupon(dto.getCouponId());
        }
        // TODO 订单总金额为零时 要做特殊处理
        ticketOrderMapper.insert(order);
        orderVisitorService.addVisitor(ProductType.TICKET, order.getId(), dto.getVisitorList());
        scenicTicketService.updateStock(ticket.getId(), dto.getNum());
        orderHandlerService.sendOrderExpireMessage(order.getOrderNo());
    }

    @Override
    public TicketOrder selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return ticketOrderMapper.selectOne(wrapper);
    }

    @Override
    public TicketOrder selectByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOutTradeNo, outTradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return ticketOrderMapper.selectOne(wrapper);
    }

    @Override
    public TicketOrder getUnPayById(Long id) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null) {
            log.error("门票订单已被删除 [{}]", id);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        if (order.getState() != OrderState.UN_PAY) {
            log.error("门票订单状态不是待支付 [{}] [{}]", id, order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        return order;
    }

    @Override
    public TradeState getOrderPayState(TicketOrder order) {
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
    public void applyRefund(ApplyTicketRefundDTO dto) {
        TicketOrder order = ticketOrderMapper.selectById(dto.getOrderId());

        this.checkRefundApply(dto, order);

        // 是否走退款审批判断(未设置)
        OrderRefundLog log = DataUtil.copy(dto, OrderRefundLog.class);
        log.setApplyTime(LocalDateTime.now());
        log.setAuditState(AuditState.APPLY);
        orderRefundLogService.insert(log);

        order.setRefundState(RefundState.APPLY);
        ticketOrderMapper.updateById(order);

        orderVisitorService.lockVisitor(ProductType.TICKET, dto.getOrderId(), log.getId(), dto.getVisitorIds());
    }

    @Override
    public void auditRefund(AuditTicketRefundRequest request) {
        TicketOrder order = ticketOrderMapper.selectById(request.getOrderId());
        if (order.getRefundState() == null) {
            log.error("该笔订单未发起退款,无法进行审核操作 [{}] [{}]", request.getOrderId(), order.getState());
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
        int refundNum = orderRefundLogService.getTotalRefundNum(request.getOrderId());
        if ((refundNum + refundLog.getNum()) > order.getNum()) {
            log.error("门票累计退款金额(含本次)大于总支付金额 [{}] [{}] [{}]", order.getNum(), refundNum, refundLog.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX);
        }
        refundLog.setAuditRemark(request.getAuditRemark());
        refundLog.setAuditTime(LocalDateTime.now());
        if (request.getState().intValue() == AuditState.REFUSE.getValue()) {
            order.setRefundState(RefundState.REFUSE);
            refundLog.setAuditState(AuditState.REFUSE);
            orderVisitorService.unlockVisitor(request.getOrderId(), refundLog.getId());
        } else {
            order.setRefundState(RefundState.PROGRESS);
            refundLog.setAuditState(AuditState.PASS);
            refundLog.setState(0);
            refundLog.setRefundAmount(request.getRefundAmount());
            refundLog.setOutRefundNo(IdWorker.getIdStr());
            this.doRefund(refundLog, order);
        }
        ticketOrderMapper.updateById(order);
        orderRefundLogService.updateById(refundLog);
    }

    @Override
    public PrepayVO prepay(Long orderId, String buyerId, TradeType tradeType) {
        TicketOrder order = this.getUnPayById(orderId);
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
        ticketOrderMapper.updateById(order);
        return vo;
    }

    @Override
    public void orderExpire(String orderNo) {
        TicketOrder order = this.selectByOrderNo(orderNo);
        if (order == null) {
            log.error("门票订单已被删除 [{}]", orderNo);
            return;
        }
        if (order.getState() != OrderState.UN_PAY) {
            log.error("门票订单状态不是待支付, 无需处理 [{}] [{}]", orderNo, order.getState());
            return;
        }
        this.doCloseOrder(order, CloseType.EXPIRE);
    }

    @Override
    public void orderCancel(Long orderId) {
        TicketOrder order = this.getUnPayById(orderId);
        TradeState state = this.getOrderPayState(order);
        if (state != TradeState.NOT_PAY && state != TradeState.WAIT_BUYER_PAY) {
            log.error("订单已支付,无法直接关闭 [{}] [{}]", orderId, state);
            throw new BusinessException(ErrorCode.ORDER_PAID_CANCEL);
        }
        this.doCloseOrder(order, CloseType.CANCEL);
    }

    @Override
    public void orderPaying(Long orderId, Long userId) {
        LambdaUpdateWrapper<TicketOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(TicketOrder::getState, OrderState.PROGRESS);
        wrapper.set(TicketOrder::getPayTime, LocalDateTime.now());
        wrapper.eq(TicketOrder::getId, orderId);
        wrapper.eq(TicketOrder::getState, OrderState.UN_PAY);
        wrapper.eq(TicketOrder::getUserId, userId);
        ticketOrderMapper.update(null, wrapper);
    }

    @Override
    public void orderDelete(Long orderId, Long userId) {
        LambdaUpdateWrapper<TicketOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TicketOrder::getId, orderId);
        wrapper.eq(TicketOrder::getState, OrderState.CLOSE);
        wrapper.eq(TicketOrder::getUserId, userId);
        ticketOrderMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void orderPay(String orderNo) {
        TicketOrder order = this.selectByOrderNo(orderNo);
        if (order.getState() != OrderState.PROGRESS) {
            log.error("订单状态已更改,无须更新支付状态 [{}] [{}]", orderNo, order.getState());
            return;
        }
        TradeType tradeType = TradeType.valueOf(order.getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, order.getOutTradeNo());
        if (vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS) {
            order.setState(OrderState.UN_USED);
            ticketOrderMapper.updateById(order);
        } else {
            log.error("异步通知查询订单状态非支付成功 [{}] [{}] [{}]", order.getId(), order.getOutTradeNo(), vo.getTradeState());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void orderRefund(String outTradeNo, String outRefundNo) {
        TicketOrder ticketOrder = this.selectByOutTradeNo(outTradeNo);
        if (ticketOrder == null) {
            log.error("根据支付流水号未查询到门票订单,不做退款处理 [{}] [{}]", outTradeNo, outRefundNo);
            return;
        }
        OrderRefundLog refundLog = orderRefundLogService.selectByOutRefundNo(outRefundNo);
        if (refundLog == null) {
            log.error("根据退款流水号未查询到退款记录,不做退款处理 [{}] [{}]", outTradeNo, outRefundNo);
            return;
        }

        TradeType tradeType = TradeType.valueOf(ticketOrder.getPayType().name());
        RefundVO refund = aggregatePayService.queryRefund(tradeType, outTradeNo, outRefundNo);
        com.eghm.service.pay.enums.RefundState state = refund.getState();
        if (state == REFUND_SUCCESS || state == SUCCESS) {
            this.setOrderState(ticketOrder);
            ticketOrder.setRefundState(RefundState.SUCCESS);
            refundLog.setState(1);
        } else if (state == ABNORMAL || state == CLOSED) {
            ticketOrder.setRefundState(RefundState.FAIL);
            refundLog.setState(2);
        }
        orderRefundLogService.updateById(refundLog);
        ticketOrderMapper.updateById(ticketOrder);
    }

    @Override
    public List<String> getPayingList() {
        return ticketOrderMapper.getPayingList();
    }

    /**
     * 根据退款记录及核销记录计算订单的最终状态
     * @param order 订单信息
     */
    private void setOrderState(TicketOrder order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(order.getId());
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

    /**
     * 发起退款操作
     * @param log 退款记录
     * @param order 门票订单
     */
    private void doRefund(OrderRefundLog log, TicketOrder order) {
        RefundDTO dto = new RefundDTO();
        dto.setOutRefundNo(log.getOutRefundNo());
        dto.setOutTradeNo(order.getOutTradeNo());
        dto.setReason(log.getReason());
        dto.setAmount(log.getRefundAmount());
        dto.setTradeType(TradeType.valueOf(order.getPayType().name()));
        aggregatePayService.applyRefund(dto);
    }

    /**
     * 校验退款申请
     * @param dto 退款信息
     * @param order 订单信息
     */
    private void checkRefundApply(ApplyTicketRefundDTO dto, TicketOrder order) {
        if (!dto.getUserId().equals(order.getUserId())) {
            log.error("订单不属于当前用户,无法退款 [{}] [{}] [{}]", dto.getOrderId(), order.getUserId(), dto.getUserId());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (Boolean.FALSE.equals(order.getSupportRefund())) {
            log.error("该门票不支持退款 [{}]", dto.getOrderId());
            throw new BusinessException(ErrorCode.TICKET_REFUND_SUPPORTED);
        }
        if (order.getState() != OrderState.UN_USED) {
            log.error("门票订单状态不是待使用,无法退款 [{}] [{}]", dto.getOrderId(), order.getState());
            throw new BusinessException(ErrorCode.TICKET_STATE_REFUND);
        }
        if (order.getRefundState() != null && order.getRefundState() != RefundState.REFUSE) {
            log.error("门票订单退款状态非法 [{}] [{}]", dto.getOrderId(), order.getRefundState());
            throw new BusinessException(ErrorCode.TICKET_REFUND_INVALID);
        }
        // 实名制
        if (Boolean.TRUE.equals(order.getRealBuy()) && dto.getNum() != dto.getVisitorIds().size()) {
            log.error("退款数量和退款人数不一致 [{}] [{}] [{}]", dto.getOrderId(), dto.getNum(), dto.getVisitorIds().size());
            throw new BusinessException(ErrorCode.TICKET_REFUND_VISITOR);
        }
    }

    /**
     * 校验门票是否符合购买条件
     * @param ticket 门票信息
     * @param dto 购买信息
     */
    private void checkTicket(ScenicTicket ticket, CreateTicketOrderDTO dto) {
        if (ticket.getStock() - dto.getNum() < 0) {
            log.error("门票库存不足 [{}] [{}] [{}]", ticket.getId(), ticket.getStock(), dto.getNum());
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
        if (ticket.getQuota() < dto.getNum()) {
            log.error("超出门片单次购买上限 [{}] [{}] [{}]", ticket.getId(), ticket.getQuota(), dto.getNum());
            throw new BusinessException(ErrorCode.TICKET_QUOTA.getCode(), String.format(ErrorCode.TICKET_QUOTA.getMsg(), ticket.getQuota()));
        }
        // 待补充用户信息
        if (Boolean.TRUE.equals(ticket.getRealBuy()) && (CollUtil.isEmpty(dto.getVisitorList()) || dto.getVisitorList().size() != dto.getNum())) {
            log.error("实名制购票录入游客信息不匹配 [{}]", ticket.getId());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
    }


    /**
     * 订单关闭
     * 1.增库存
     * 2.释放优惠券(如果有的话)
     * 3.更新订单状态
     * @param order 订单信息
     * @param closeType 关闭方式
     */
    private void doCloseOrder(TicketOrder order, CloseType closeType) {
        order.setState(OrderState.CLOSE);
        order.setCloseType(closeType);
        order.setCloseTime(LocalDateTime.now());
        ticketOrderMapper.updateById(order);
        userCouponService.releaseCoupon(order.getCouponId());
        scenicTicketService.updateStock(order.getTicketId(), -order.getNum());
    }
}
