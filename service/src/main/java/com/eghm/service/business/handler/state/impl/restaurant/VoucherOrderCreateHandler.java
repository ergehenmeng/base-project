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
import com.eghm.model.VoucherOrder;
import com.eghm.model.Voucher;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.VoucherOrderCreateContext;
import com.eghm.service.business.handler.dto.VoucherOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/24
 */
@Service("voucherOrderCreateHandler")
@Slf4j
public class VoucherOrderCreateHandler extends AbstractOrderCreateHandler<VoucherOrderCreateContext, VoucherOrderPayload> {

    private final VoucherService voucherService;

    private final RestaurantService restaurantService;

    private final VoucherOrderService voucherOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public VoucherOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, RestaurantService restaurantService, OrderMQService orderMQService, VoucherService voucherService, VoucherOrderService voucherOrderService) {
        super(memberCouponService, orderVisitorService);
        this.restaurantService = restaurantService;
        this.voucherService = voucherService;
        this.voucherOrderService = voucherOrderService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected VoucherOrderPayload getPayload(VoucherOrderCreateContext context) {
        Voucher voucher = voucherService.selectByIdShelve(context.getVoucherId());
        Restaurant restaurant = restaurantService.selectByIdShelve(voucher.getRestaurantId());
        VoucherOrderPayload payload = new VoucherOrderPayload();
        payload.setVoucher(voucher);
        payload.setRestaurant(restaurant);
        return payload;
    }

    @Override
    protected void before(VoucherOrderCreateContext context, VoucherOrderPayload payload) {
        Integer num = context.getNum();
        Voucher voucher = payload.getVoucher();
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
    protected Order createOrder(VoucherOrderCreateContext context, VoucherOrderPayload payload) {
        Voucher voucher = payload.getVoucher();
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
        order.setNickName(context.getNickName());
        order.setMobile(context.getMobile());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), voucher.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(VoucherOrderCreateContext context, VoucherOrderPayload payload) {
        return payload.getVoucher().getHotSell();
    }

    @Override
    protected void queueOrder(VoucherOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.VOUCHER_ORDER, context);
    }

    @Override
    protected void next(VoucherOrderCreateContext context, VoucherOrderPayload payload, Order order) {
        voucherService.updateStock(context.getVoucherId(), -context.getNum());
        VoucherOrder voucherOrder = DataUtil.copy(payload.getVoucher(), VoucherOrder.class, "id");
        voucherOrder.setOrderNo(order.getOrderNo());
        voucherOrder.setVoucherId(context.getVoucherId());
        voucherOrderService.insert(voucherOrder);
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected void end(VoucherOrderCreateContext context, VoucherOrderPayload payload, Order order) {
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
