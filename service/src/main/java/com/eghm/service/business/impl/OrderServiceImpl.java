package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderMapper;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderService;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Service("orderService")
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final AggregatePayService aggregatePayService;

    @Override
    public PrepayVO createPrepay(String orderNo, String buyerId, TradeType tradeType) {
        List<String> orderNoList = StrUtil.split(orderNo, ',');
        List<Order> orderList = this.getUnPay(orderNoList);
        String outTradeNo = ProductType.ITEM.generateTradeNo();
        StringBuilder builder = new StringBuilder();
        int totalAmount = 0;
        for (Order order : orderList) {
            // 支付方式先占坑
            order.setPayType(PayType.valueOf(tradeType.name()));
            order.setOutTradeNo(outTradeNo);
            builder.append(order.getTitle()).append(",");
            totalAmount += order.getPayAmount();
            baseMapper.updateById(order);
        }

        PrepayDTO dto = new PrepayDTO();
        dto.setAttach(orderNo);
        dto.setDescription(this.getGoodsTitle(builder));
        dto.setTradeType(tradeType);
        dto.setAmount(totalAmount);
        dto.setOutTradeNo(outTradeNo);
        dto.setBuyerId(buyerId);
        return aggregatePayService.createPrepay(dto);
    }

    @Override
    public Order selectByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getOutTradeNo, outTradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Order> selectByOutTradeNoList(String outTradeNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getOutTradeNo, outTradeNo);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Order selectById(Long orderId) {
        return baseMapper.selectById(orderId);
    }

    @Override
    public List<Order> getUnPay(List<String> orderNoList) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.select(Order::getId, Order::getTitle, Order::getPayAmount);
        wrapper.in(Order::getOrderNo, orderNoList);
        wrapper.eq(Order::getState, OrderState.UN_PAY);
        List<Order> orderList = baseMapper.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            log.error("订单可能已被删除 {}", orderNoList);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        if (orderList.size() != orderNoList.size()) {
            log.error("存在部分订单不是待支付 [{}]", orderNoList);
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        return orderList;
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
        Order order = baseMapper.selectOne(wrapper);
        if (order == null) {
            log.error("订单可能已被删除 [{}]", orderNo);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        return order;
    }

    @Override
    public void setProcess(Long orderId, Long userId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Order::getState, OrderState.PROGRESS);
        wrapper.set(Order::getPayTime, LocalDateTime.now());
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.UN_PAY);
        wrapper.eq(Order::getUserId, userId);
        baseMapper.update(null, wrapper);
    }

    @Override
    public void deleteOrder(Long orderId, Long userId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.CLOSE);
        wrapper.eq(Order::getUserId, userId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<Order> getProcessList() {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.select(Order::getOrderNo, Order::getProductType);
        wrapper.eq(Order::getState, OrderState.PROGRESS);
        return baseMapper.selectList(wrapper);
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
    public void updateState(List<String> orderNoList, OrderState newState, OrderState... oldState) {
        if (CollUtil.isEmpty(orderNoList)) {
            log.error("订单号为空, 无法更新订单状态 [{}]", newState);
        }
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(Order::getOrderNo, orderNoList);
        wrapper.eq(oldState.length > 0, Order::getState, oldState);
        wrapper.set(Order::getState, newState);
        int update = baseMapper.update(null, wrapper);
        if (update != orderNoList.size()) {
            log.warn("订单状态更新数据不一致 [{}] [{}] [{}]", orderNoList, newState, oldState);
        }
    }

    @Override
    public void updateState(String orderNo, OrderState newState, OrderState... oldState) {
        this.updateState(Lists.newArrayList(orderNo), newState, oldState);
    }

    /**
     * 支付时创建商品名称
     * @param builder builder
     * @return 商品名称
     */
    private String getGoodsTitle(StringBuilder builder) {
        if (builder.length() > 50) {
            return builder.substring(0, 50);
        }
        return builder.toString();
    }
}
