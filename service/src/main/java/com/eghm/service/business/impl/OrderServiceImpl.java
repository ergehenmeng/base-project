package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.configuration.SystemProperties;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.ticket.TicketOfflineRefundRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderMapper;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.OfflineRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Service("orderService")
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final AggregatePayService aggregatePayService;

    private final SystemProperties systemProperties;

    private final OfflineRefundLogService offlineRefundLogService;

    private final OrderVisitorService orderVisitorService;

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
    public void setProcess(Long orderId, Long memberId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Order::getState, OrderState.PROGRESS);
        wrapper.set(Order::getPayTime, LocalDateTime.now());
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.UN_PAY);
        wrapper.eq(Order::getMemberId, memberId);
        baseMapper.update(null, wrapper);
    }

    @Override
    public void deleteOrder(Long orderId, Long memberId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getId, orderId);
        wrapper.eq(Order::getState, OrderState.CLOSE);
        wrapper.eq(Order::getMemberId, memberId);
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
        dto.setOrderNo(order.getOrderNo());
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
    @Override
    public String decryptVerifyNo(String verifyNo) {
        AES aes = SecureUtil.aes(systemProperties.getApi().getSecretKey().getBytes(StandardCharsets.UTF_8));
        String decryptStr;
        try {
            decryptStr = aes.decryptStr(verifyNo);
        } catch (Exception e) {
            log.error("核销码解析错误 [{}]", verifyNo, e);
            throw new BusinessException(ErrorCode.VERIFY_NO_ERROR);
        }
        String[] split = decryptStr.split(CommonConstant.ENCRYPT_SPLIT);
        long theTime = Long.parseLong(split[0]);
        if (CommonConstant.MAX_VERIFY_NO_EXPIRE < (System.currentTimeMillis() - theTime)) {
            throw new BusinessException(ErrorCode.VERIFY_EXPIRE_ERROR);
        }
        return split[1];
    }

    @Override
    public String encryptVerifyNo(String orderNo) {
        AES aes = SecureUtil.aes(systemProperties.getApi().getSecretKey().getBytes(StandardCharsets.UTF_8));
        return aes.encryptHex(System.currentTimeMillis() + CommonConstant.ENCRYPT_SPLIT + orderNo);
    }

    @Override
    public void ticketOfflineRefund(TicketOfflineRefundRequest request) {
        Order order = this.getByOrderNo(request.getOrderNo());
        if (order.getState() == OrderState.UN_PAY) {
            log.warn("订单未支付, 不支持线下退款 [{}]", request.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_UN_PAY);
        }
        if (order.getState() == OrderState.CLOSE) {
            log.warn("订单已关闭, 不支持线下退款 [{}]", request.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_CLOSE_REFUND);
        }
        this.checkHasRefund(request.getVisitorList(), request.getOrderNo());
        // TODO


    }

    /**
     * 校验用户列表中是否存在已经线下退款的人,如果存在则给出提示
     * @param visitorList 待线下退款的用户
     * @param orderNo 订单号
     */
    private void checkHasRefund(List<Long> visitorList, String orderNo) {
        List<OrderVisitor> visitors = orderVisitorService.getByIds(visitorList, orderNo);
        if (visitorList.size() != visitors.size()) {
            log.error("订单号与游客id不匹配,可能不属于同一个订单 [{}] [{}]", orderNo, visitorList);
            throw new BusinessException(ErrorCode.MEMBER_REFUND_MATCH);
        }
        List<Long> refundVisitorIds = offlineRefundLogService.getTicketRefundLog(orderNo);
        List<OrderVisitor> hasRefundList = visitors.stream().filter(orderVisitor -> refundVisitorIds.contains(orderVisitor.getId())).collect(Collectors.toList());
        if (!hasRefundList.isEmpty()) {
            String userList = hasRefundList.stream().map(OrderVisitor::getMemberName).collect(Collectors.joining(","));
            throw new BusinessException(ErrorCode.MEMBER_HAS_REFUND.getCode(), String.format(ErrorCode.MEMBER_HAS_REFUND.getMsg(), userList));
        }
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
