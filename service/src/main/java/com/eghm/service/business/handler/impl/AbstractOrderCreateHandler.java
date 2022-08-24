package com.eghm.service.business.handler.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.dao.model.Order;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.OrderMQService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.handler.OrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderCreateHandler<T> implements OrderCreateHandler {

    private final OrderService orderService;

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderMQService;

    /**
     * @param dto 订单信息
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Async
    public void process(OrderCreateDTO dto) {
        T product = this.getProduct(dto);
        this.before(dto, product);
        // 主订单下单
        Order order = this.doProcess(dto, product);
        if (order != null) {
            this.next(dto, product, order);
            this.after(dto, product, order);
        } else {
            log.info("该商品为热销商品,走MQ队列处理");
        }
    }

    /**
     * 订单创建后置处理,默认定时任务
     * @param dto 下单信息
     * @param product 商品信息
     * @param order 主订单信息
     */
    protected void after(OrderCreateDTO dto, T product, Order order) {
        orderMQService.sendOrderExpireMessage(order.getOrderNo());
    }

    /**
     * 主订单创建后分类订单创建等
     * @param dto 下单信息
     * @param product 商品信息
     * @param order 主订单信息
     */
    protected abstract void next(OrderCreateDTO dto, T product, Order order);

    /**
     * 下单
     * @param dto 订单信息
     * @param product 商品信息
     * @return 主订单信息
     */
    private Order doProcess(OrderCreateDTO dto, T product) {
        BaseProduct base = this.getBaseProduct(dto, product);
        if (Boolean.TRUE.equals(base.getHotSell())) {
            // 走MQ形式
            return null;
        }

        String orderNo = base.getProductType().getPrefix() + IdWorker.getIdStr();
        Order order = DataUtil.copy(dto, Order.class);
        order.setTitle(base.getTitle());
        order.setOrderNo(orderNo);
        order.setPrice(base.getSalePrice());
        order.setProductType(ProductType.TICKET);
        order.setRefundType(base.getRefundType());
        order.setPayAmount(dto.getNum() * base.getSalePrice());

        if (Boolean.TRUE.equals(base.getSupportedCoupon()) && dto.getCouponId() != null) {
            Integer couponAmount = userCouponService.getCouponAmountWithVerify(dto.getUserId(), dto.getCouponId(), dto.getProductId(), order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setCouponId(dto.getCouponId());
            userCouponService.useCoupon(dto.getCouponId());
        }
        orderService.insert(order);
        // 门票订单关联购票人信息
        orderVisitorService.addVisitor(base.getProductType(), orderNo, dto.getVisitorList());
        return order;
    }

    /**
     * 获取商品信息
     * @param dto 下单信息
     * @return 商品信息
     */
    protected abstract T getProduct(OrderCreateDTO dto);

    /**
     * 获取商品基础信息,即各类商品的公告信息
     * @param dto 下单信息
     * @param product 商品详细信息
     * @return 基础信息
     */
    protected abstract BaseProduct getBaseProduct(OrderCreateDTO dto, T product);

    /**
     * 下单前置校验
     * @param dto 下单信息
     * @param product 商品信息
     */
    protected abstract void before(OrderCreateDTO dto, T product);
}
