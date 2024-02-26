package com.eghm.service.business.handler.state.impl.venue;

import com.eghm.dto.ext.VenuePhase;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.exception.BusinessException;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.VenueOrderCreateContext;
import com.eghm.service.business.handler.dto.VenueOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.service.common.JsonService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@Service("venueOrderCreateHandler")
@Slf4j
public class VenueOrderCreateHandler extends AbstractOrderCreateHandler<VenueOrderCreateContext, VenueOrderPayload> {

    private final VenueService venueService;

    private final VenueSiteService venueSiteService;

    private final VenueOrderService venueOrderService;

    private final VenueSitePriceService venueSitePriceService;

    private final JsonService jsonService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    public VenueOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, VenueService venueService, JsonService jsonService,
                                   OrderMQService orderMQService, VenueSiteService venueSiteService, VenueOrderService venueOrderService, VenueSitePriceService venueSitePriceService,
                                   RedeemCodeGrantService redeemCodeGrantService) {
        super(memberCouponService, orderVisitorService, redeemCodeGrantService);
        this.venueService = venueService;
        this.venueSiteService = venueSiteService;
        this.venueOrderService = venueOrderService;
        this.venueSitePriceService = venueSitePriceService;
        this.jsonService = jsonService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
        this.redeemCodeGrantService = redeemCodeGrantService;
    }

    @Override
    protected VenueOrderPayload getPayload(VenueOrderCreateContext context) {
        List<VenueSitePrice> priceList = venueSitePriceService.listByIds(context.getPhaseList());
        if (priceList.isEmpty()) {
            log.error("未找到对应的场地价格 [{}]", context.getPhaseList());
            throw new BusinessException(ErrorCode.VENUE_STOCK);
        }
        Set<Long> venueList = priceList.stream().map(VenueSitePrice::getVenueId).collect(Collectors.toSet());
        if (venueList.size() > 1) {
            log.error("不支持多场馆预约 [{}]", context.getPhaseList());
            throw new BusinessException(ErrorCode.VENUE_MULTI);
        }
        Set<Long> siteList = priceList.stream().map(VenueSitePrice::getVenueSiteId).collect(Collectors.toSet());
        if (siteList.size() > 1) {
            log.error("不支持跨场地预约 [{}]", context.getPhaseList());
            throw new BusinessException(ErrorCode.VENUE_SITE_MULTI);
        }
        Set<LocalDate> dateList = priceList.stream().map(VenueSitePrice::getNowDate).collect(Collectors.toSet());
        if (dateList.size() > 1) {
            log.error("不支持跨天预约 [{}]", context.getPhaseList());
            throw new BusinessException(ErrorCode.VENUE_SKIP);
        }
        VenueSitePrice price = priceList.get(0);
        Venue venue = venueService.selectByIdShelve(price.getVenueId());
        VenueSite venueSite = venueSiteService.selectByIdShelve(price.getVenueSiteId());
        Integer redeemAmount = redeemCodeGrantService.getRedeemAmount(context.getCdKey(), venue.getId(), price.getVenueSiteId());
        VenueOrderPayload payload = new VenueOrderPayload();
        payload.setCdKeyAmount(redeemAmount);
        payload.setVenue(venue);
        payload.setVenueSite(venueSite);
        payload.setPriceList(priceList);
        return payload;
    }

    @Override
    protected void before(VenueOrderCreateContext context, VenueOrderPayload payload) {
        int size = context.getPhaseList().size();
        List<VenueSitePrice> priceList = payload.getPriceList().stream().filter(phase -> phase.getStock() > 0 && phase.getState()).collect(Collectors.toList());
        if (size != priceList.size()) {
            log.error("时间段可能已被预约,库存不足 [{}] [{}]", context.getPhaseList(), payload.getPriceList());
            throw new BusinessException(ErrorCode.VENUE_STOCK);
        }
    }

    @Override
    protected Order createOrder(VenueOrderCreateContext context, VenueOrderPayload payload) {
        Venue venue = payload.getVenue();
        Order order = DataUtil.copy(context, Order.class);
        order.setCoverUrl(venue.getCoverUrl());
        order.setMerchantId(venue.getMerchantId());
        order.setStoreId(venue.getId());
        order.setState(OrderState.UN_PAY);
        order.setOrderNo(ProductType.VENUE.generateOrderNo());
        order.setNum(1);
        order.setTitle(venue.getTitle() + "/" + payload.getVenueSite().getTitle());
        order.setPrice(payload.getPriceList().stream().mapToInt(VenueSitePrice::getPrice).sum());
        order.setPayAmount(order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        order.setProductType(ProductType.VENUE);
        order.setRefundType(RefundType.DIRECT_REFUND);
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), venue.getId());
        // 使用cdKey
        super.useRedeemCode(order, context.getMemberId(), context.getCdKey(), payload.getCdKeyAmount());
        // 校验最低金额
        super.checkAmount(order);
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(VenueOrderCreateContext context, VenueOrderPayload payload) {
        return false;
    }

    @Override
    protected void queueOrder(VenueOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.VOUCHER_ORDER, context);
    }

    @Override
    protected void next(VenueOrderCreateContext context, VenueOrderPayload payload, Order order) {
        venueSitePriceService.updateStock(context.getPhaseList(), -1);
        VenueOrder venueOrder = DataUtil.copy(payload.getVenue(), VenueOrder.class, "id");
        venueOrder.setOrderNo(order.getOrderNo());
        venueOrder.setMemberId(context.getMemberId());
        venueOrder.setVenueId(payload.getVenue().getId());
        venueOrder.setVenueSiteId(payload.getVenueSite().getId());
        venueOrder.setVisitDate(payload.getPriceList().get(0).getNowDate());
        venueOrder.setSiteTitle(payload.getVenueSite().getTitle());
        List<VenuePhase> phaseList = DataUtil.copy(payload.getPriceList(), VenuePhase.class);
        venueOrder.setTimePhase(jsonService.toJson(phaseList));
        venueOrderService.insert(venueOrder);
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected void end(VenueOrderCreateContext context, VenueOrderPayload payload, Order order) {
        if (order.getPayAmount() <= 0) {
            log.info("场馆预约免费,订单号:{}", order.getOrderNo());
            orderService.paySuccess(order.getOrderNo(), order.getProductType().generateVerifyNo(), OrderState.UN_USED, order.getState());
        } else {
            orderMQService.sendOrderExpireMessage(ExchangeQueue.VENUE_PAY_EXPIRE, order.getOrderNo());
        }
    }

    @Override
    protected Integer getLowestAmount() {
        return 0;
    }

    @Override
    public IEvent getEvent() {
        return VenueEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.VENUE;
    }

}
