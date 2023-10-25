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
import com.eghm.model.Restaurant;
import com.eghm.model.RestaurantOrder;
import com.eghm.model.RestaurantVoucher;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.RestaurantOrderCreateContext;
import com.eghm.service.business.handler.dto.RestaurantOrderPayload;
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
public class RestaurantOrderCreateHandler extends AbstractOrderCreateHandler<RestaurantOrderCreateContext, RestaurantOrderPayload> {

    private final RestaurantVoucherService restaurantVoucherService;

    private final RestaurantService restaurantService;

    private final RestaurantOrderService restaurantOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public RestaurantOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, RestaurantService restaurantService, OrderMQService orderMQService, RestaurantVoucherService restaurantVoucherService, RestaurantOrderService restaurantOrderService) {
        super(memberCouponService, orderVisitorService);
        this.restaurantService = restaurantService;
        this.restaurantVoucherService = restaurantVoucherService;
        this.restaurantOrderService = restaurantOrderService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected RestaurantOrderPayload getPayload(RestaurantOrderCreateContext context) {
        RestaurantVoucher voucher = restaurantVoucherService.selectByIdShelve(context.getVoucherId());
        Restaurant restaurant = restaurantService.selectByIdShelve(voucher.getRestaurantId());
        RestaurantOrderPayload payload = new RestaurantOrderPayload();
        payload.setRestaurantVoucher(voucher);
        payload.setRestaurant(restaurant);
        return payload;
    }

    @Override
    protected void before(RestaurantOrderCreateContext context, RestaurantOrderPayload payload) {
        Integer num = context.getNum();
        RestaurantVoucher voucher = payload.getRestaurantVoucher();
        if (voucher.getStock() - num < 0) {
            log.error("餐饮券库存不足 [{}] [{}] [{}]", voucher.getId(), voucher.getStock(), num);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
        if (voucher.getQuota() < num) {
            log.error("超出餐饮券单次购买上限 [{}] [{}] [{}]", voucher.getId(), voucher.getQuota(), num);
            throw new BusinessException(ErrorCode.VOUCHER_QUOTA.getCode(), String.format(ErrorCode.VOUCHER_QUOTA.getMsg(), voucher.getQuota()));
        }
    }

    @Override
    protected Order createOrder(RestaurantOrderCreateContext context, RestaurantOrderPayload payload) {
        RestaurantVoucher voucher = payload.getRestaurantVoucher();
        String orderNo = ProductType.RESTAURANT.generateOrderNo();
        Order order = DataUtil.copy(context, Order.class);
        order.setCoverUrl(voucher.getCoverUrl());
        order.setMerchantId(payload.getRestaurant().getMerchantId());
        order.setStoreId(payload.getRestaurant().getId());
        order.setState(OrderState.UN_PAY);
        order.setMemberId(context.getMemberId());
        order.setOrderNo(orderNo);
        order.setRemark(context.getRemark());
        order.setNum(context.getNum());
        order.setTitle(voucher.getTitle());
        order.setPrice(voucher.getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        order.setProductType(ProductType.RESTAURANT);
        order.setRefundType(voucher.getRefundType());
        order.setRefundDescribe(voucher.getRefundDescribe());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), voucher.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(RestaurantOrderCreateContext context, RestaurantOrderPayload payload) {
        return payload.getRestaurantVoucher().getHotSell();
    }

    @Override
    protected void queueOrder(RestaurantOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.RESTAURANT_ORDER, context);
    }

    @Override
    protected void next(RestaurantOrderCreateContext context, RestaurantOrderPayload payload, Order order) {
        restaurantVoucherService.updateStock(context.getVoucherId(), -context.getNum());
        RestaurantOrder restaurantOrder = DataUtil.copy(payload.getRestaurantVoucher(), RestaurantOrder.class, "id");
        restaurantOrder.setOrderNo(order.getOrderNo());
        restaurantOrder.setMobile(context.getMobile());
        restaurantOrder.setVoucherId(context.getVoucherId());
        restaurantOrderService.insert(restaurantOrder);
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected void end(RestaurantOrderCreateContext context, RestaurantOrderPayload payload, Order order) {
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
