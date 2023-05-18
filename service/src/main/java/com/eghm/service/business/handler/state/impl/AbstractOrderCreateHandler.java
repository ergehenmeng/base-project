package com.eghm.service.business.handler.state.impl;

import com.eghm.model.Order;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.service.business.handler.state.OrderCreateHandler;
import com.eghm.state.machine.Context;
import com.eghm.utils.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderCreateHandler<C extends Context, P> implements OrderCreateHandler<C> {

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doAction(C context) {
        P payload = this.getPayload(context);
        this.before(context, payload);
        // 主订单下单
        Order order = this.doProcess(context, payload);
        if (order == null) {
            return;
        }
        this.next(context, payload, order);
        TransactionUtil.afterCommit(() -> this.sendMsg(context, payload, order));
    }

    /**
     * 订单创建后置处理,默认过期自动取消定时任务
     * @param context 下单信息
     * @param payload 商品信息
     * @param order 主订单信息
     */
    protected abstract void sendMsg(C context, P payload, Order order);

    /**
     * 下单
     * @param context 订单信息
     * @param payload 商品相关信息
     * @return 主订单信息
     */
    protected Order doProcess(C context, P payload) {
        if (this.isHotSell(context, payload)) {
            log.info("该商品为热销商品,走MQ队列处理");
            // 消息队列在事务之外发送减少事务持有时间
            TransactionUtil.afterCommit(() -> this.createOrderUseQueue(context));
            return null;
        }
        return this.createOrder(context, payload);
    }


    /**
     * 下单时关联订单绑定游客信息
     * @param order 订单信息
     * @param visitorList 游客信息
     */
    protected void addVisitor(Order order, List<VisitorDTO> visitorList) {
        // 订单关联购买人信息
        orderVisitorService.addVisitor(order.getProductType(), order.getOrderNo(), visitorList);
    }

    /**
     * 是否为热销商品, 热销商品走mq下单,增加并发, 非热销商品直接下单
     * @param context 下单信息上下文
     * @param payload 下单关联信息
     * @return true 热销商品 false: 不是热销商品
     */
    public boolean isHotSell(C context, P payload) {
        return true;
    }

    /**
     * 通过消息队列进行下单
     * @param context 下单信息
     */
    protected void createOrderUseQueue(C context) {

    }

    /**
     * 使用抵扣金额
     * @param order 订单信息,不含优惠金额
     * @param userId 用户id
     * @param couponId 优惠券id
     * @param productId 商品id
     */
    public void useDiscount(Order order, Long userId, Long couponId, Long productId) {
        if (couponId != null) {
            log.info("订单[{}]使用了优惠券 [{}]", order.getOrderNo(), couponId);
            Integer couponAmount = userCouponService.getCouponAmountWithVerify(userId, couponId, productId, order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setCouponId(couponId);
            userCouponService.useCoupon(couponId);
        }
    }

    /**
     * 获取商品信息
     * @param context 下单信息
     * @return 商品信息
     */
    protected abstract P getPayload(C context);

    /**
     * 下单前置校验
     * @param context 下单信息
     * @param payload 商品信息
     */
    protected abstract void before(C context, P payload);

    /**
     * 创建订单
     * @param context 下单信息
     * @param payload 商品详细信息
     * @return 下单所需的必要信息
     */
    protected abstract Order createOrder(C context, P payload);

    /**
     * 主订单创建后分类订单创建等
     * @param context 下单信息
     * @param payload 商品信息
     * @param order 主订单信息
     */
    protected abstract void next(C context, P payload, Order order);

}
