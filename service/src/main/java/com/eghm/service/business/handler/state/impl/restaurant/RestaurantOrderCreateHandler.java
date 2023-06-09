package com.eghm.service.business.handler.state.impl.restaurant;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.RestaurantEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.RestaurantOrder;
import com.eghm.model.RestaurantVoucher;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RestaurantOrderCreateContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/24
 */
@Service("restaurantOrderCreateHandler")
@Slf4j
public class RestaurantOrderCreateHandler extends AbstractOrderCreateHandler<RestaurantOrderCreateContext, RestaurantVoucher> {

    private final RestaurantVoucherService restaurantVoucherService;

    private final RestaurantOrderService restaurantOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public RestaurantOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, RestaurantVoucherService restaurantVoucherService, RestaurantOrderService restaurantOrderService) {
        super(memberCouponService, orderVisitorService);
        this.restaurantVoucherService = restaurantVoucherService;
        this.restaurantOrderService = restaurantOrderService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected RestaurantVoucher getPayload(RestaurantOrderCreateContext context) {
        return restaurantVoucherService.selectByIdShelve(context.getVoucherId());
    }

    @Override
    protected void before(RestaurantOrderCreateContext context, RestaurantVoucher payload) {
        Integer num = context.getNum();
        if (payload.getStock() - num < 0) {
            log.error("餐饮券库存不足 [{}] [{}] [{}]", payload.getId(), payload.getStock(), num);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
        if (payload.getQuota() < num) {
            log.error("超出餐饮券单次购买上限 [{}] [{}] [{}]", payload.getId(), payload.getQuota(), num);
            throw new BusinessException(ErrorCode.VOUCHER_QUOTA.getCode(), String.format(ErrorCode.VOUCHER_QUOTA.getMsg(), payload.getQuota()));
        }
    }

    @Override
    protected Order createOrder(RestaurantOrderCreateContext context, RestaurantVoucher payload) {
        String orderNo = ProductType.RESTAURANT.generateOrderNo();
        Order order = DataUtil.copy(context, Order.class);
        order.setCoverUrl(super.getFirstCoverUrl(payload.getCoverUrl()));
        order.setState(OrderState.UN_PAY);
        order.setMemberId(context.getMemberId());
        order.setOrderNo(orderNo);
        order.setNum(context.getNum());
        order.setTitle(payload.getTitle());
        order.setPrice(payload.getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        order.setRefundType(payload.getRefundType());
        order.setRefundDescribe(payload.getRefundDescribe());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), payload.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(RestaurantOrderCreateContext context, RestaurantVoucher payload) {
        return payload.getHotSell();
    }

    @Override
    protected void queueOrder(RestaurantOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.RESTAURANT_ORDER, context);
    }

    @Override
    protected void next(RestaurantOrderCreateContext context, RestaurantVoucher payload, Order order) {
        restaurantVoucherService.updateStock(context.getVoucherId(), -context.getNum());
        RestaurantOrder restaurantOrder = DataUtil.copy(payload, RestaurantOrder.class);
        restaurantOrder.setOrderNo(order.getOrderNo());
        restaurantOrder.setVoucherId(context.getVoucherId());
        restaurantOrderService.insert(restaurantOrder);
    }

    @Override
    protected void end(RestaurantOrderCreateContext context, RestaurantVoucher payload, Order order) {
        orderMQService.sendOrderExpireMessage(ExchangeQueue.RESTAURANT_PAY_EXPIRE, order.getOrderNo());
    }


    @Override
    public IEvent getEvent() {
        return RestaurantEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.RESTAURANT;
    }

}
