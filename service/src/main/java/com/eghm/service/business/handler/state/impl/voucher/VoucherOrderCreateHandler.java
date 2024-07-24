package com.eghm.service.business.handler.state.impl.voucher;

import com.eghm.common.OrderMQService;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.Restaurant;
import com.eghm.model.Voucher;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.VoucherOrderCreateContext;
import com.eghm.service.business.handler.dto.VoucherOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/8/24
 */
@Service("voucherOrderCreateHandler")
@Slf4j
public class VoucherOrderCreateHandler extends AbstractOrderCreateHandler<VoucherOrderCreateContext, VoucherOrderPayload> {

    private final VoucherService voucherService;

    private final RestaurantService restaurantService;

    private final VoucherOrderService voucherOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public VoucherOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, RestaurantService restaurantService, OrderMQService orderMQService, VoucherService voucherService, VoucherOrderService voucherOrderService, RedeemCodeGrantService redeemCodeGrantService) {
        super(memberCouponService, orderVisitorService, redeemCodeGrantService);
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
            throw new BusinessException(ErrorCode.VOUCHER_QUOTA, voucher.getQuota());
        }
    }

    @Override
    protected Order createOrder(VoucherOrderCreateContext context, VoucherOrderPayload payload) {
        Voucher voucher = payload.getVoucher();
        Order order = DataUtil.copy(context, Order.class);
        order.setCoverUrl(voucher.getCoverUrl());
        order.setMerchantId(payload.getRestaurant().getMerchantId());
        order.setStoreId(payload.getRestaurant().getId());
        order.setState(OrderState.UN_PAY);
        order.setOrderNo(ProductType.VOUCHER.generateOrderNo());
        order.setTitle(voucher.getTitle());
        order.setPrice(voucher.getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setAmount(order.getNum() * order.getPrice());
        order.setMultiple(false);
        order.setProductType(ProductType.VOUCHER);
        order.setRefundType(RefundType.DIRECT_REFUND);
        order.setCreateDate(LocalDate.now());
        order.setCreateTime(LocalDateTime.now());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), voucher.getId());
        // 校验最低金额
        super.checkAmount(order);
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
        voucherOrder.setMemberId(context.getMemberId());
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
        return VoucherEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VOUCHER;
    }

}
