package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.HomestayOrder;
import com.eghm.model.HomestayRoom;
import com.eghm.model.HomestayRoomConfig;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.HomestayOrderCreateContext;
import com.eghm.service.business.handler.dto.HomestayOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 民宿下单默认全部都队列模式
 * @author 二哥很猛
 * @date 2022/8/22
 */
@Service("homestayOrderCreateQueueHandler")
@Slf4j
public class HomestayOrderCreateQueueHandler extends AbstractOrderCreateHandler<HomestayOrderCreateContext, HomestayOrderPayload> {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomService homestayRoomService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final HomestayOrderSnapshotService homestayOrderSnapshotService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public HomestayOrderCreateQueueHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService, HomestayRoomConfigService homestayRoomConfigService, HomestayOrderSnapshotService homestayOrderSnapshotService) {
        super(userCouponService, orderVisitorService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomService = homestayRoomService;
        this.homestayRoomConfigService = homestayRoomConfigService;
        this.homestayOrderSnapshotService = homestayOrderSnapshotService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
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
        return HomestayEvent.CREATE_QUEUE;
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
        List<HomestayRoomConfig> configList = homestayRoomConfigService.getList(context.getRoomId(), context.getStartDate(), context.getEndDate());
        HomestayOrderPayload payload = new HomestayOrderPayload();
        payload.setHomestayRoom(homestayRoom);
        payload.setConfigList(configList);
        return payload;
    }

    @Override
    protected Order createOrder(HomestayOrderCreateContext context, HomestayOrderPayload payload) {
        String orderNo = ProductType.HOMESTAY.generateOrderNo();
        Order order = DataUtil.copy(context, Order.class);
        order.setState(OrderState.UN_PAY);
        order.setUserId(context.getUserId());
        order.setOrderNo(orderNo);
        order.setNum(context.getNum());
        order.setTitle(payload.getHomestayRoom().getTitle());
        // 将每天的价格相加=总单价
        int salePrice = payload.getConfigList().stream().mapToInt(HomestayRoomConfig::getSalePrice).sum();
        order.setPrice(salePrice);
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        // 使用优惠券
        this.useDiscount(order, context.getUserId(), context.getCouponId(), context.getRoomId());
        orderService.save(order);
        return order;
    }


    @Override
    protected void next(HomestayOrderCreateContext context, HomestayOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        homestayRoomConfigService.updateStock(context.getRoomId(), context.getStartDate(), context.getEndDate(), -context.getNum());
        HomestayOrder homestayOrder = DataUtil.copy(payload.getHomestayRoom(), HomestayOrder.class);
        homestayOrder.setOrderNo(order.getOrderNo());
        homestayOrder.setMobile(context.getMobile());
        homestayOrder.setStartDate(context.getStartDate());
        homestayOrder.setEndDate(context.getEndDate());
        homestayOrder.setRoomId(context.getRoomId());
        homestayOrderService.insert(homestayOrder);
        homestayOrderSnapshotService.orderSnapshot(order.getOrderNo(), payload.getConfigList());
    }

    @Override
    protected void end(HomestayOrderCreateContext context, HomestayOrderPayload payload, Order order) {
        orderMQService.sendOrderExpireMessage(ExchangeQueue.HOMESTAY_PAY_EXPIRE, order.getOrderNo());
    }

}
