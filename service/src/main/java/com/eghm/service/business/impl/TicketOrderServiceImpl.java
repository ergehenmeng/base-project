package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CloseType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.PayType;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.TicketOrderMapper;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.ApplyTicketRefundDTO;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;
import com.eghm.service.business.*;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Service("ticketOrderService")
@AllArgsConstructor
@Slf4j
public class TicketOrderServiceImpl implements TicketOrderService, OrderService {

    private final TicketOrderMapper ticketOrderMapper;

    private final ScenicTicketService scenicTicketService;

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderHandlerService;

    private final AggregatePayService aggregatePayService;

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
        ticketOrderMapper.insert(order);
        orderVisitorService.addVisitor(ProductType.TICKET, order.getId(), dto.getVisitorList());
        scenicTicketService.updateStock(ticket.getId(), dto.getNum());
        orderHandlerService.sendOrderExpireMessage(order.getOrderNo());
    }

    @Override
    public TicketOrder selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOrderNo, orderNo);
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
        TicketOrder order = ticketOrderMapper.selectById(dto.getId());
        if (!dto.getUserId().equals(order.getUserId())) {
            log.error("订单不属于当前用户,无法退款 [{}] [{}] [{}]", dto.getId(), order.getUserId(), dto.getUserId());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        // TODO 是否支持退款判断
        // 是否走退款审批判断(未设置)
        // 发起退款申请
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
    public void orderRefund(String outTradeNo, String outRefundNo) {
        // TODO 支付回调待完成
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
