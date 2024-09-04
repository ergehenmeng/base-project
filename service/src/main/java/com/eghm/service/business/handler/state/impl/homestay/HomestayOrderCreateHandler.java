package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.common.OrderMQService;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ConfirmState;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.HomestayOrderCreateContext;
import com.eghm.service.business.handler.dto.HomestayOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 民宿下单默认全部都队列模式
 *
 * @author 二哥很猛
 * @since 2022/8/22
 */
@Service("homestayOrderCreateHandler")
@Slf4j
public class HomestayOrderCreateHandler extends AbstractOrderCreateHandler<HomestayOrderCreateContext, HomestayOrderPayload> {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomService homestayRoomService;

    private final HomestayService homestayService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final HomestayOrderSnapshotService homestayOrderSnapshotService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    private final RedeemCodeGrantService redeemCodeGrantService;

    public HomestayOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService,
                                      HomestayService homestayService, HomestayRoomConfigService homestayRoomConfigService, HomestayOrderSnapshotService homestayOrderSnapshotService, RedeemCodeGrantService redeemCodeGrantService) {
        super(memberCouponService, orderVisitorService, redeemCodeGrantService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomService = homestayRoomService;
        this.homestayService = homestayService;
        this.homestayRoomConfigService = homestayRoomConfigService;
        this.homestayOrderSnapshotService = homestayOrderSnapshotService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
        this.redeemCodeGrantService = redeemCodeGrantService;
    }

    @Override
    protected void before(HomestayOrderCreateContext context, HomestayOrderPayload payload) {
        List<HomestayRoomConfig> configList = payload.getConfigList();
        long size = ChronoUnit.DAYS.between(context.getStartDate(), context.getEndDate());
        if (configList.size() < size) {
            log.error("该时间段房房态不满足下单数量 [{}] [{}] [{}] [{}]", context.getRoomId(), context.getStartDate(), context.getEndDate(), size);
            throw new BusinessException(ErrorCode.HOMESTAY_CONFIG_NULL);
        }
        boolean match = configList.stream().anyMatch(config -> Boolean.FALSE.equals(config.getState()) || config.getStock() <= 0);
        if (match) {
            log.error("房间库存不足 [{}] [{}] [{}]", context.getRoomId(), context.getStartDate(), context.getEndDate());
            throw new BusinessException(ErrorCode.HOMESTAY_STOCK);
        }
    }

    @Override
    public IEvent getEvent() {
        return HomestayEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }

    @Override
    protected void queueOrder(HomestayOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.HOMESTAY_ORDER, context);
    }

    @Override
    protected HomestayOrderPayload getPayload(HomestayOrderCreateContext context) {
        HomestayRoom homestayRoom = homestayRoomService.selectByIdShelve(context.getRoomId());
        Homestay homestay = homestayService.selectByIdShelve(homestayRoom.getHomestayId());
        List<HomestayRoomConfig> configList = homestayRoomConfigService.getList(context.getRoomId(), context.getStartDate(), context.getEndDate());
        Integer redeemAmount = redeemCodeGrantService.getRedeemAmount(context.getCdKey(), homestay.getId(), context.getRoomId());
        HomestayOrderPayload payload = new HomestayOrderPayload();
        payload.setHomestay(homestay);
        payload.setHomestayRoom(homestayRoom);
        payload.setConfigList(configList);
        payload.setCdKeyAmount(redeemAmount);
        return payload;
    }

    @Override
    protected Order createOrder(HomestayOrderCreateContext context, HomestayOrderPayload payload) {
        Order order = DataUtil.copy(context, Order.class);
        order.setProductType(ProductType.HOMESTAY);
        order.setRefundType(payload.getHomestayRoom().getRefundType());
        order.setRefundDescribe(payload.getHomestayRoom().getRefundDescribe());
        order.setState(OrderState.UN_PAY);
        order.setMerchantId(payload.getHomestay().getMerchantId());
        order.setStoreId(payload.getHomestay().getId());
        order.setCoverUrl(payload.getHomestayRoom().getCoverUrl());
        order.setOrderNo(ProductType.HOMESTAY.generateOrderNo());
        order.setTitle(payload.getHomestayRoom().getTitle());
        // 将每天的价格相加=总单价
        int salePrice = payload.getConfigList().stream().mapToInt(HomestayRoomConfig::getSalePrice).sum();
        order.setPrice(salePrice);
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setAmount(order.getNum() * order.getPrice());
        order.setMultiple(false);
        order.setCreateDate(LocalDate.now());
        order.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        order.setCreateTime(LocalDateTime.now());
        // 使用优惠券
        super.useDiscount(order, context.getMemberId(), context.getCouponId(), context.getRoomId());
        // 使用cdKey
        super.useRedeemCode(order, context.getMemberId(), context.getCdKey(), payload.getCdKeyAmount());
        // 校验最低金额
        super.checkAmount(order);
        orderService.save(order);
        return order;
    }

    @Override
    protected void next(HomestayOrderCreateContext context, HomestayOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        homestayRoomConfigService.updateStock(context.getRoomId(), context.getStartDate(), context.getEndDate(), context.getVisitorList().size());
        HomestayOrder homestayOrder = DataUtil.copy(payload.getHomestayRoom(), HomestayOrder.class, "id");
        homestayOrder.setOrderNo(order.getOrderNo());
        homestayOrder.setStartDate(context.getStartDate());
        homestayOrder.setEndDate(context.getEndDate());
        homestayOrder.setRoomId(context.getRoomId());
        homestayOrder.setMemberId(context.getMemberId());
        if (payload.getHomestayRoom().getConfirmType() == 1) {
            homestayOrder.setConfirmState(ConfirmState.AUTO_CONFIRM);
        } else {
            homestayOrder.setConfirmState(ConfirmState.WAIT_CONFIRM);
        }
        homestayOrderService.insert(homestayOrder);
        homestayOrderSnapshotService.orderSnapshot(order.getOrderNo(), payload.getConfigList());
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected void end(HomestayOrderCreateContext context, HomestayOrderPayload payload, Order order) {
        orderMQService.sendOrderExpireMessage(ExchangeQueue.HOMESTAY_PAY_EXPIRE, order.getOrderNo());
    }

}
