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
import com.eghm.dto.business.order.OfflineRefundRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ExpressType;
import com.eghm.enums.ref.*;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderMapper;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.common.JsonService;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PayOrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.AssertUtil;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TransactionUtil;
import com.eghm.vo.business.merchant.address.MerchantAddressVO;
import com.eghm.vo.business.order.OrderScanVO;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.VisitorVO;
import com.eghm.vo.business.order.item.ExpressDetailVO;
import com.eghm.vo.business.order.item.ExpressVO;
import com.eghm.vo.business.order.item.ItemOrderRefundVO;
import com.eghm.vo.business.statistics.OrderStatisticsVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/8/17
 */
@Service("orderService")
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final AggregatePayService aggregatePayService;

    private final SystemProperties systemProperties;

    private final OfflineRefundLogService offlineRefundLogService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderMQService;

    private final CommonService commonService;

    private final ItemOrderService itemOrderService;

    private final ItemExpressService itemExpressService;

    private final JsonService jsonService;

    private final SysAreaService sysAreaService;

    private final MessageService messageService;

    private final AccountService accountService;

    private final ScoreAccountService scoreAccountService;

    private final MerchantAddressService merchantAddressService;

    @Override
    public PrepayVO createPrepay(String orderNo, String buyerId, TradeType tradeType, String clientIp) {
        List<String> orderNoList = StrUtil.split(orderNo, ',');
        ProductType productType = ProductType.prefix(orderNoList.get(0));
        String tradeNo = productType.generateTradeNo();

        List<Order> orderList = this.getUnPay(orderNoList);
        StringBuilder builder = new StringBuilder();
        int totalAmount = 0;
        for (Order order : orderList) {
            // 支付方式先占坑
            order.setPayType(PayType.valueOf(tradeType.name()));
            order.setTradeNo(tradeNo);
            builder.append(order.getTitle()).append(",");
            totalAmount += order.getPayAmount();
            baseMapper.updateById(order);
        }

        PrepayDTO dto = new PrepayDTO();
        dto.setAttach(orderNo);
        dto.setDescription(this.getGoodsTitle(builder));
        dto.setTradeType(tradeType);
        dto.setAmount(totalAmount);
        dto.setTradeNo(tradeNo);
        dto.setBuyerId(buyerId);
        dto.setClientIp(clientIp);
        return aggregatePayService.createPrepay(dto);
    }

    @Override
    public Order selectByTradeNo(String tradeNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getTradeNo, tradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Order> selectByTradeNoList(String tradeNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getTradeNo, tradeNo);
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
        if (StrUtil.isBlank(order.getTradeNo())) {
            log.info("订单未生成支付流水号 [{}]", order.getId());
            return TradeState.NOT_PAY;
        }
        PayType payType = order.getPayType();
        // 订单支付方式和支付方式需要做一层转换
        TradeType tradeType = TradeType.valueOf(payType.name());
        PayOrderVO vo = aggregatePayService.queryOrder(tradeType, order.getTradeNo());
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
    public void deleteOrder(String orderNo, Long memberId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getOrderNo, orderNo);
        wrapper.eq(Order::getState, OrderState.CLOSE);
        wrapper.eq(Order::getMemberId, memberId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<Order> getProcessList() {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.select(Order::getOrderNo, Order::getProductType, Order::getTradeNo, Order::getPayType);
        wrapper.eq(Order::getState, OrderState.PROGRESS);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void startRefund(OrderRefundLog log, Order order) {
        RefundDTO dto = new RefundDTO();
        dto.setRefundNo(log.getRefundNo());
        dto.setTradeNo(order.getTradeNo());
        dto.setReason(log.getReason());
        dto.setAmount(log.getRefundAmount());
        dto.setTradeType(TradeType.valueOf(order.getPayType().name()));
        dto.setOrderNo(order.getOrderNo());
        aggregatePayService.applyRefund(dto);
    }

    @Override
    public void updateState(List<String> orderNoList, LocalDateTime payTime, OrderState newState, Object... oldState) {
        if (CollUtil.isEmpty(orderNoList)) {
            log.error("订单号为空, 无法更新订单状态 [{}]", newState);
        }
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(Order::getOrderNo, orderNoList);
        wrapper.in(oldState.length > 0, Order::getState, oldState);
        wrapper.set(Order::getState, newState);
        wrapper.set(Order::getPayTime, payTime);
        int update = baseMapper.update(null, wrapper);
        if (update != orderNoList.size()) {
            log.warn("订单状态更新数据不一致 [{}] [{}] [{}]", orderNoList, newState, oldState);
        }
    }

    @Override
    public void updateState(String orderNo, OrderState newState, Object... oldState) {
        this.updateState(Lists.newArrayList(orderNo), null, newState, oldState);
    }

    @Override
    public void paySuccess(String orderNo, String verifyNo, LocalDateTime payTime, OrderState newState, Object... oldState) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getOrderNo, orderNo);
        wrapper.in(oldState.length > 0, Order::getState, oldState);
        wrapper.set(Order::getState, newState);
        wrapper.set(Order::getPayTime, payTime);
        wrapper.set(Order::getVerifyNo, verifyNo);
        int update = baseMapper.update(null, wrapper);
        if (update != 1) {
            log.warn("订单状态更新数据不一致 [{}] [{}] [{}]", orderNo, newState, oldState);
        }
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
        String[] split = decryptStr.split(CommonConstant.SPECIAL_SPLIT);
        long theTime = Long.parseLong(split[0]);
        if (CommonConstant.MAX_VERIFY_NO_EXPIRE < (System.currentTimeMillis() - theTime)) {
            throw new BusinessException(ErrorCode.VERIFY_EXPIRE_ERROR);
        }
        return split[1];
    }

    @Override
    public String encryptVerifyNo(String verifyNo) {
        if (verifyNo == null) {
            return null;
        }
        AES aes = SecureUtil.aes(systemProperties.getApi().getSecretKey().getBytes(StandardCharsets.UTF_8));
        return aes.encryptHex(System.currentTimeMillis() + CommonConstant.SPECIAL_SPLIT + verifyNo);
    }

    @Override
    public void offlineRefund(OfflineRefundRequest request) {
        Order order = this.getRefuningOrder(request.getOrderNo());

        boolean refundSuccess = orderRefundLogService.hasRefundSuccess(order.getOrderNo(), request.getVisitorList());

        if (refundSuccess) {
            log.warn("待线下退款的游客列表中, 存在退款中的游客 [{}] {}", request.getOrderNo(), request.getVisitorList());
            throw new BusinessException(ErrorCode.MEMBER_HAS_REFUNDING);
        }

        this.checkHasRefund(request.getVisitorList(), request.getOrderNo());

        offlineRefundLogService.insertLog(request);
        orderVisitorService.updateRefund(request.getVisitorList(), request.getOrderNo());
        // 计算主订单状态
        OrderState orderState = orderVisitorService.getOrderState(order.getOrderNo());
        order.setState(orderState);
        if (order.getState() == OrderState.CLOSE) {
            order.setCloseTime(LocalDateTime.now());
            order.setCloseType(CloseType.REFUND);
        }
        this.orderStateModify(order);
        baseMapper.updateById(order);
    }

    @Override
    public OrderScanVO getScanResult(String verifyNo, Long merchantId) {
        Order order = this.getByVerifyNo(verifyNo);
        if (commonService.checkIsIllegal(merchantId)) {
            log.warn("核销码不属于当前商户下的订单 [{}] [{}] [{}]", verifyNo, merchantId, order.getMerchantId());
            throw new BusinessException(ErrorCode.VERIFY_ACCESS_DENIED);
        }
        OrderScanVO vo = DataUtil.copy(order, OrderScanVO.class);
        List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(order.getOrderNo());
        List<VisitorVO> voList = DataUtil.copy(visitorList, VisitorVO.class);
        vo.setVisitorList(voList);
        return vo;
    }

    @Override
    public void orderStateModify(Order order) {
        Order databaseOrder = this.getByOrderNo(order.getOrderNo());
        if (databaseOrder.getState() == order.getState()) {
            log.warn("订单状态未发生改变,不做处理 [{}]", order.getOrderNo());
            return;
        }
        if (order.getState() == OrderState.COMPLETE) {
            order.setCompleteTime(LocalDateTime.now());
            TransactionUtil.afterCommit(() -> {
                if (order.getProductType() == ProductType.TICKET) {
                    orderMQService.sendOrderCompleteMessage(ExchangeQueue.TICKET_COMPLETE_DELAY, order.getOrderNo());
                } else if (order.getProductType() == ProductType.ITEM) {
                    orderMQService.sendOrderCompleteMessage(ExchangeQueue.ITEM_COMPLETE_DELAY, order.getOrderNo());
                } else if (order.getProductType() == ProductType.LINE) {
                    orderMQService.sendOrderCompleteMessage(ExchangeQueue.LINE_COMPLETE_DELAY, order.getOrderNo());
                } else if (order.getProductType() == ProductType.RESTAURANT) {
                    orderMQService.sendOrderCompleteMessage(ExchangeQueue.RESTAURANT_COMPLETE_DELAY, order.getOrderNo());
                } else if (order.getProductType() == ProductType.HOMESTAY) {
                    orderMQService.sendOrderCompleteMessage(ExchangeQueue.HOMESTAY_COMPLETE_DELAY, order.getOrderNo());
                }
            });
        }
    }

    @Override
    public List<ProductSnapshotVO> getProductList(String orderNo) {
        ProductType productType = ProductType.prefix(orderNo);
        if (productType == ProductType.ITEM) {
            return baseMapper.getItemList(orderNo);
        } else if (productType == ProductType.RESTAURANT) {
            return baseMapper.getRestaurantList(orderNo);
        } else if (productType == ProductType.TICKET) {
            return baseMapper.getTicketList(orderNo);
        } else if (productType == ProductType.LINE) {
            return baseMapper.getLineList(orderNo);
        } else if (productType == ProductType.HOMESTAY) {
            return baseMapper.getHomestayList(orderNo);
        }
        return Lists.newArrayList();
    }

    @Override
    public String refreshVerifyCode(String orderNo, Long memberId) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.select(Order::getId, Order::getVerifyNo);
        wrapper.eq(Order::getMemberId, memberId);
        wrapper.eq(Order::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        Order order = baseMapper.selectOne(wrapper);
        if (order == null) {
            log.error("刷新核销码, 订单信息未查询到 [{}] [{}]", orderNo, memberId);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        return this.encryptVerifyNo(order.getVerifyNo());
    }

    @Override
    public Order getByVerifyNo(String verifyNo) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Order::getVerifyNo, verifyNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        Order order = baseMapper.selectOne(wrapper);
        if (order == null) {
            log.error("根据核销码查询订单信息为空 [{}]", verifyNo);
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        return order;
    }

    @Override
    public void sipping(ItemSippingRequest request) {
        Order order = this.getByOrderNo(request.getOrderNo());
        if (order.getState() != OrderState.WAIT_DELIVERY && order.getState() != OrderState.PARTIAL_DELIVERY) {
            log.error("订单状态已发生变化,不支持发货 [{}] [{}]", request.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        List<ItemOrder> orderList = itemOrderService.getByIds(request.getOrderIds());
        if (orderList.isEmpty()) {
            log.error("发货订单信息查询为空 [{}]", request.getOrderIds());
            throw new BusinessException(ErrorCode.ORDER_ITEM_CHOOSE);
        }
        boolean anyMatch = orderList.stream().anyMatch(itemOrder -> itemOrder.getRefundState() == ItemRefundState.REFUND);
        if (anyMatch) {
            log.error("发货订单中存在已退款的订单 [{}]", request.getOrderIds());
            throw new BusinessException(ErrorCode.CHOOSE_NO_REFUND);
        }
        anyMatch = orderList.stream().anyMatch(itemOrder -> itemOrder.getDeliveryState() == DeliveryState.WAIT_DELIVERY);
        if (anyMatch) {
            log.error("发货订单中存在已发货的订单 [{}]", request.getOrderIds());
            throw new BusinessException(ErrorCode.CHOOSE_NO_DELIVERY);
        }
        anyMatch = orderList.stream().anyMatch(itemOrder -> itemOrder.getDeliveryType() != DeliveryType.EXPRESS);
        if (anyMatch) {
            log.error("发货订单中存在无需发货的订单 [{}]", request.getOrderIds());
            throw new BusinessException(ErrorCode.CHOOSE_EXPRESS);
        }
        orderList.forEach(itemOrder -> itemOrder.setDeliveryState(DeliveryState.WAIT_TAKE));
        itemOrderService.updateBatchById(orderList);
        itemExpressService.insert(request);

        Long count = itemOrderService.countWaitDelivery(request.getOrderNo());
        if (count == 0) {
            order.setState(OrderState.WAIT_RECEIVE);
            messageService.sendDelay(ExchangeQueue.ITEM_SIPPING, order.getOrderNo(), 14);
        } else {
            order.setState(OrderState.PARTIAL_DELIVERY);
        }
        baseMapper.updateById(order);
    }

    @Override
    public ExpressDetailVO expressDetail(Long id) {
        ItemExpress express = itemExpressService.selectById(id);
        if (express == null) {
            log.info("未查询到快递信息 [{}]", id);
            throw new BusinessException(ErrorCode.EXPRESS_SELECT_NULL);
        }
        Order order = this.getByOrderNo(express.getOrderNo());

        ExpressDetailVO vo = new ExpressDetailVO();
        vo.setExpressNo(express.getExpressNo());
        vo.setExpressName(ExpressType.of(express.getExpressCode()).getName());
        vo.setExpressList(jsonService.fromJsonList(express.getContent(), ExpressVO.class));
        vo.setNickName(order.getNickName());
        vo.setMobile(order.getMobile());
        vo.setDetailAddress(sysAreaService.parseArea(order.getProvinceId(), order.getCityId(), order.getCountyId()) + order.getDetailAddress());
        return vo;
    }

    @Override
    public void confirmReceipt(String orderNo, Long memberId) {
        log.info("零售商品确认收货 [{}] [{}]", orderNo, memberId);
        Order order = this.getByOrderNo(orderNo);
        if (memberId != null && !memberId.equals(order.getMemberId())) {
            log.error("操作了不属于自己的确认订单 [{}] [{}]", orderNo, memberId);
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (order.getState() != OrderState.WAIT_RECEIVE) {
            log.error("订单状态已发生变化,不支持确认收货 [{}] [{}] [{}]", memberId, orderNo, order.getState());
            if (memberId == null) {
                return;
            }
            throw new BusinessException(ErrorCode.ORDER_PAID);
        }
        order.setCompleteTime(LocalDateTime.now());
        order.setState(OrderState.COMPLETE);
        baseMapper.updateById(order);
        orderMQService.sendOrderCompleteMessage(ExchangeQueue.ITEM_COMPLETE_DELAY, order.getOrderNo());
    }

    @Override
    public void routing(String orderNo) {
        log.info("开始进行订单分账: [{}]", orderNo);
        Order order = this.getByOrderNo(orderNo);
        if (order.getState() != OrderState.COMPLETE) {
            log.error("订单状态状态不是待完成,不支持分账 [{}] [{}]", orderNo, order.getState());
            return;
        }
        if (Boolean.TRUE.equals(order.getSettleState())) {
            log.error("订单已结算,不支持分账 [{}]", orderNo);
            return;
        }
        int routingAmount = order.getPayAmount() - order.getRefundAmount();
        if (routingAmount <= 0) {
            log.error("订单结算金额小于0,不支持分账 [{}] [{}]", orderNo, routingAmount);
            return;
        }
        // 分账
        log.error("分账功能待补全 [{}] 待分账金额: [{}]", routingAmount, orderNo);
        accountService.orderComplete(order.getMerchantId(), order.getOrderNo());
        accountService.orderComplete(systemProperties.getPlatformMerchantId(), order.getOrderNo());
        log.error("分账功能待补全 [{}] 待分账积分: [{}]", routingAmount, orderNo);
        scoreAccountService.orderComplete(order.getMerchantId(), routingAmount);
    }

    @Override
    public OrderStatisticsVO orderStatistics(DateRequest request) {
        return baseMapper.orderStatistics(request);
    }

    @Override
    public List<OrderStatisticsVO> dayOrder(DateRequest request) {
        List<OrderStatisticsVO> voList = baseMapper.dayOrder(request);
        Map<LocalDate, OrderStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(OrderStatisticsVO::getCreateDate, Function.identity()));
        return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), OrderStatisticsVO::new);
    }

    @Override
    public void updateBookingState(String bookingNo, Integer bookingState) {
        log.info("修改订单拼团状态: [{}] [{}]", bookingNo, bookingState);
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getBookingNo, bookingNo);
        wrapper.set(Order::getBookingState, bookingState);
        baseMapper.update(null, wrapper);
    }

    @Override
    public void checkGroupOrder(String bookingNo, Long memberId) {
        LambdaUpdateWrapper<Order> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Order::getBookingNo, bookingNo);
        wrapper.eq(Order::getMemberId, memberId);
        wrapper.ne(Order::getState, OrderState.CLOSE);
        Long count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("用户已在拼团订单,不支持重复下单 [{}] [{}]", bookingNo, memberId);
            throw new BusinessException(ErrorCode.BOOKING_REDO_ORDER);
        }
    }


    @Override
    public ItemOrderRefundVO getItemRefund(Long orderId, Long memberId, boolean containAddress) {
        ItemOrderRefundVO refund = itemOrderService.getRefund(orderId, memberId);
        AssertUtil.assertOrderNotNull(refund, orderId, memberId);
        Order order = this.getByOrderNo(refund.getOrderNo());
        if (order.getState() == OrderState.UN_PAY || order.getState() == OrderState.CLOSE || order.getState() == OrderState.PROGRESS || order.getState() == OrderState.PAY_ERROR) {
            log.error("订单未支付,不支持退款 [{}] [{}]", order.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.ORDER_NOT_PAY);
        }
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(refund.getOrderNo());
        if (orderList.size() > 1) {
            List<OrderRefundLog> refundList = orderRefundLogService.getRefundLog(refund.getOrderNo());
            int refundNum = refundList.size() + 1;
            if (orderList.size() <= refundNum) {
                log.info("零售最后一次退款 [{}]", order.getOrderNo());
                int refundAmount = refundList.stream().mapToInt(OrderRefundLog::getRefundAmount).sum();
                int scoreAmount = refundList.stream().mapToInt(OrderRefundLog::getScoreAmount).sum();
                refund.setScoreAmount(order.getScoreAmount() - scoreAmount);
                refund.setRefundAmount(order.getPayAmount() - refundAmount);
            } else {
                int totalAmount = orderList.stream().mapToInt(value -> value.getNum() * value.getSalePrice()).sum();
                Integer itemAmount = refund.getNum() * refund.getSalePrice();
                Integer refundAmount = calcRateAmount(itemAmount, totalAmount, order.getDiscountAmount());
                refund.setRefundAmount(itemAmount - refundAmount);
                Integer scoreAmount = calcRateAmount(itemAmount, totalAmount, order.getScoreAmount());
                refund.setScoreAmount(scoreAmount);
            }
        } else {
            refund.setRefundAmount(order.getPayAmount());
            refund.setScoreAmount(order.getScoreAmount());
        }
        // 已经发货的快递费不退
        if (refund.getDeliveryState() == DeliveryState.CONFIRM_TASK || refund.getDeliveryState() == DeliveryState.WAIT_TAKE) {
            refund.setExpressFeeAmount(0);
        }
        // 只有待收货的商品才需要额外携带收货地址方便用户填写邮寄地址
        if (containAddress && (refund.getDeliveryState() == DeliveryState.WAIT_TAKE || refund.getDeliveryState() == DeliveryState.CONFIRM_TASK)) {
            MerchantAddressVO address = merchantAddressService.getAddress(order.getStoreId());
            refund.setStoreAddress(address);
        }
        return refund;
    }

    /**
     * 按比例计算退款金额或积分
     *
     * @param saleAmount 商品售价
     * @param totalAmount 订单总价
     * @param amount 优惠金额或积分
     * @return 优惠金额或积分
     */
    private static Integer calcRateAmount(Integer saleAmount, Integer totalAmount, Integer amount) {
        return BigDecimal.valueOf((long) saleAmount * amount).divide(BigDecimal.valueOf(totalAmount), 2, RoundingMode.HALF_DOWN).intValue();
    }


    /**
     * 获取待退款的订单信息
     *
     * @param orderNo 订单信息
     * @return 订单信息
     */
    private Order getRefuningOrder(String orderNo) {
        Order order = this.getByOrderNo(orderNo);
        if (order.getState() == OrderState.UN_PAY) {
            log.warn("订单未支付, 不支持退款 [{}]", orderNo);
            throw new BusinessException(ErrorCode.ORDER_UN_PAY);
        }
        if (order.getState() == OrderState.CLOSE) {
            log.warn("订单已关闭, 不支持退款 [{}]", orderNo);
            throw new BusinessException(ErrorCode.ORDER_CLOSE_REFUND);
        }
        return order;
    }

    /**
     * 校验用户列表中是否存在已经线下退款的人,如果存在则给出提示
     *
     * @param visitorList 待线下退款的用户
     * @param orderNo     订单号
     */
    private void checkHasRefund(List<Long> visitorList, String orderNo) {
        List<OrderVisitor> visitors = orderVisitorService.getByIds(visitorList, orderNo);
        if (visitorList.size() != visitors.size()) {
            log.error("订单号与游客id不匹配,可能不属于同一个订单 [{}] [{}]", orderNo, visitorList);
            throw new BusinessException(ErrorCode.MEMBER_REFUND_MATCH);
        }
        List<Long> refundVisitorIds = offlineRefundLogService.getRefundLog(orderNo);
        List<OrderVisitor> hasRefundList = visitors.stream().filter(orderVisitor -> refundVisitorIds.contains(orderVisitor.getId())).collect(Collectors.toList());
        if (!hasRefundList.isEmpty()) {
            String userList = hasRefundList.stream().map(OrderVisitor::getMemberName).collect(Collectors.joining(","));
            throw new BusinessException(ErrorCode.MEMBER_HAS_REFUND.getCode(), String.format(ErrorCode.MEMBER_HAS_REFUND.getMsg(), userList));
        }
    }

    /**
     * 支付时创建商品名称
     *
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
