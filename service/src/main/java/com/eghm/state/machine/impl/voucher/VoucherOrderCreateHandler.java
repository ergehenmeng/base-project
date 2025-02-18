package com.eghm.state.machine.impl.voucher;

import com.eghm.common.OrderMqService;
import com.eghm.enums.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.Restaurant;
import com.eghm.model.Voucher;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.*;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.access.impl.VoucherAccessHandler;
import com.eghm.state.machine.context.VoucherOrderCreateContext;
import com.eghm.state.machine.dto.VoucherOrderPayload;
import com.eghm.state.machine.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.utils.SpringContextUtil;
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

    private final OrderService orderService;

    private final VoucherService voucherService;

    private final RestaurantService restaurantService;

    private final VoucherOrderService voucherOrderService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    public VoucherOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, RestaurantService restaurantService, OrderMqService orderMqService, VoucherService voucherService, VoucherOrderService voucherOrderService, RedeemCodeGrantService redeemCodeGrantService) {
        super(orderMqService, memberCouponService, orderVisitorService, redeemCodeGrantService);
        this.orderService = orderService;
        this.voucherService = voucherService;
        this.restaurantService = restaurantService;
        this.voucherOrderService = voucherOrderService;
        this.redeemCodeGrantService = redeemCodeGrantService;
    }

    @Override
    protected VoucherOrderPayload getPayload(VoucherOrderCreateContext context) {
        Voucher voucher = voucherService.selectByIdShelve(context.getVoucherId());
        Restaurant restaurant = restaurantService.selectByIdShelve(voucher.getRestaurantId());
        Integer redeemAmount = redeemCodeGrantService.getRedeemAmount(context.getCdKey(), restaurant.getId(), voucher.getId());
        VoucherOrderPayload payload = new VoucherOrderPayload();
        payload.setVoucher(voucher);
        payload.setRestaurant(restaurant);
        payload.setCdKeyAmount(redeemAmount);
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
        order.setTradeNo(ProductType.VOUCHER.generateTradeNo());
        order.setTitle(voucher.getTitle());
        order.setPrice(voucher.getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setAmount(order.getNum() * order.getPrice());
        order.setMultiple(false);
        order.setProductType(ProductType.VOUCHER);
        order.setRefundType(RefundType.DIRECT_REFUND);
        order.setCreateDate(LocalDate.now());
        order.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        order.setCreateTime(LocalDateTime.now());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), voucher.getId());
        // 使用兑换码
        this.useRedeemCode(order, context.getMemberId(), context.getCdKey(), payload.getCdKeyAmount());
        // 校验最低金额
        super.checkAmount(order);
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(VoucherOrderPayload payload) {
        return payload.getVoucher().getHotSell();
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
    protected AbstractAccessHandler getAccessHandler() {
        return SpringContextUtil.getBean(VoucherAccessHandler.class);
    }

    @Override
    protected ExchangeQueue getExchangeQueue() {
        return ExchangeQueue.RESTAURANT_PAY_EXPIRE;
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
