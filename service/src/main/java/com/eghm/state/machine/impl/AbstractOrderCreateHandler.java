package com.eghm.state.machine.impl;

import com.eghm.common.OrderMQService;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;
import com.eghm.model.Order;
import com.eghm.pay.enums.TradeType;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.RedeemCodeGrantService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.Context;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.utils.TransactionUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 订单创建 默认实现
 *
 * @author 二哥很猛
 * @since 2022/8/21
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderCreateHandler<C extends Context, P> implements ActionHandler<C> {

    private final OrderMQService orderMQService;

    private final MemberCouponService memberCouponService;

    private final OrderVisitorService orderVisitorService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    @Override
    public void doAction(C context) {
        P payload = this.getPayload(context);
        this.before(context, payload);
        // 主订单下单
        Order order = this.doProcess(context, payload);
        if (order == null) {
            return;
        }
        this.next(context, payload, order);
        this.end(order);
    }

    /**
     * 下单
     *
     * @param context 订单信息
     * @param payload 商品相关信息
     * @return 主订单信息
     */
    protected Order doProcess(C context, P payload) {
        if (this.isHotSell(context, payload)) {
            log.info("该商品为热销商品,走MQ队列处理");
            // 消息队列在事务之外发送减少事务持有时间
            TransactionUtil.afterCommit(() -> orderMQService.sendOrderCreateMessage(getExchangeQueue(), (AsyncKey) context));
            return null;
        }
        return this.createOrder(context, payload);
    }

    /**
     * 下单时关联订单绑定游客信息
     *
     * @param order       订单信息
     * @param visitorList 游客信息
     */
    protected void addVisitor(Order order, List<VisitorDTO> visitorList) {
        // 订单关联购买人信息
        orderVisitorService.addVisitor(order.getProductType(), order.getOrderNo(), visitorList);
    }

    /**
     * 是否为热销商品, 热销商品走mq下单,增加并发, 非热销商品直接下单
     *
     * @param context 下单信息上下文
     * @param payload 下单关联信息
     * @return true 热销商品 false: 不是热销商品
     */
    public boolean isHotSell(C context, P payload) {
        return true;
    }

    /**
     * 使用抵扣金额
     *
     * @param order     订单信息,不含优惠金额
     * @param memberId  用户id
     * @param couponId  优惠券id
     * @param productId 商品id
     */
    protected void useDiscount(Order order, Long memberId, Long couponId, Long productId) {
        if (couponId != null) {
            log.info("订单[{}]使用了优惠券 [{}]", order.getOrderNo(), couponId);
            Integer couponAmount = memberCouponService.getCouponAmountWithVerify(memberId, couponId, Lists.newArrayList(productId), order.getStoreId(), order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setDiscountAmount(couponAmount);
            order.setCouponId(couponId);
            memberCouponService.useCoupon(couponId);
        }
    }

    /**
     * 兑换码金额
     *
     * @param order     订单信息,不含优惠金额
     * @param memberId  用户id
     * @param cdKey  cdKey
     * @param cdKeyAmount 兑换码金额
     */
    protected void useRedeemCode(Order order, Long memberId, String cdKey, Integer cdKeyAmount) {
        if (cdKey != null) {
            log.info("订单[{}]使用了兑换码 [{}]", order.getOrderNo(), cdKey);
            order.setPayAmount(order.getPayAmount() - cdKeyAmount);
            order.setCdKey(cdKey);
            order.setCdKeyAmount(cdKeyAmount);
            redeemCodeGrantService.useRedeem(cdKey, memberId);
        }
    }

    /**
     * 设置最低实付金额
     * @param order 订单信息
     */
    protected void checkAmount(Order order) {
        if (order.getPayAmount() < 0) {
            log.info("订单实付金额小于0, 强制最小支付一分钱 [{}] [{}]", order.getOrderNo(), order.getPayAmount());
            order.setPayAmount(this.getLowestAmount());
        }
    }

    /**
     * 当使用优惠券等后, 实付金额小于0时的最低实付金额
     * @return 默认1分钱
     */
    protected Integer getLowestAmount() {
        return 1;
    }

    /**
     * 获取商品信息
     *
     * @param context 下单信息
     * @return 商品信息
     */
    protected abstract P getPayload(C context);

    /**
     * 下单前置校验
     *
     * @param context 下单信息
     * @param payload 商品信息
     */
    protected abstract void before(C context, P payload);

    /**
     * 创建订单
     *
     * @param context 下单信息
     * @param payload 商品详细信息
     * @return 下单所需的必要信息
     */
    protected abstract Order createOrder(C context, P payload);

    /**
     * 主订单创建后分类订单创建等
     *
     * @param context 下单信息
     * @param payload 商品信息
     * @param order   主订单信息
     */
    protected abstract void next(C context, P payload, Order order);

    /**
     * 获取对应品类的涉及支付的handler
     *
     * @return accessHandler
     */
    protected abstract AbstractAccessHandler getAccessHandler();

    /**
     * 获取发送消息的队列名称
     *
     * @return 队列信息
     */
    protected abstract ExchangeQueue getExchangeQueue();

    /**
     * 订单创建后置处理, 默认过期自动取消定时任务. 零元付时默认支付成功
     *
     * @param order   主订单信息
     */
    protected void end(Order order) {
        if (order.getPayAmount() <= 0) {
            log.info("订单是零元购商品,订单号:{}", order.getOrderNo());
            PayNotifyContext notify = new PayNotifyContext();
            notify.setTradeNo(order.getTradeNo());
            notify.setOrderNo(order.getOrderNo());
            notify.setSuccessTime(LocalDateTime.now());
            notify.setAmount(order.getPayAmount());
            notify.setTradeType(TradeType.ZERO_PAY);
            notify.setFrom(order.getState().getValue());
            this.getAccessHandler().paySuccess(notify);
        } else {
            orderMQService.sendOrderExpireMessage(this.getExchangeQueue(), order.getOrderNo());
        }
    }

}
