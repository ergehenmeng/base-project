package com.eghm.service.business.handler.impl.homestay;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.StateMachineType;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.HomestayOrder;
import com.eghm.model.HomestayRoom;
import com.eghm.model.HomestayRoomConfig;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.dto.HomestayOrderCreateContext;
import com.eghm.service.business.handler.dto.HomestayOrderPayload;
import com.eghm.service.business.handler.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/22
 */
@Service("homestayOrderCreateHandler")
@Slf4j
public class HomestayOrderCreateHandler extends AbstractOrderCreateHandler<HomestayOrderCreateContext, HomestayOrderPayload> {

    private final HomestayOrderService homestayOrderService;

    private final HomestayRoomService homestayRoomService;

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final HomestayOrderSnapshotService homestayOrderSnapshotService;

    private final OrderService orderService;

    public HomestayOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, HomestayOrderService homestayOrderService, HomestayRoomService homestayRoomService, HomestayRoomConfigService homestayRoomConfigService, HomestayOrderSnapshotService homestayOrderSnapshotService) {
        super(userCouponService, orderVisitorService, orderMQService);
        this.homestayOrderService = homestayOrderService;
        this.homestayRoomService = homestayRoomService;
        this.homestayRoomConfigService = homestayRoomConfigService;
        this.homestayOrderSnapshotService = homestayOrderSnapshotService;
        this.orderService = orderService;
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
        return null;
    }

    @Override
    public StateMachineType getStateMachineType() {
        return null;
    }

    @Override
    protected HomestayOrderPayload getPayload(HomestayOrderCreateContext context) {
        HomestayRoom homestayRoom = homestayRoomService.selectByIdShelve(context.getRoomId());
        List<HomestayRoomConfig> configList = homestayRoomConfigService.getList(context.getRoomId(), context.getStartDate(), context.getEndDate());
        HomestayOrderPayload product = new HomestayOrderPayload();
        product.setHomestayRoom(homestayRoom);
        product.setConfigList(configList);
        return product;
    }

    @Override
    protected Order createOrder(HomestayOrderCreateContext context, HomestayOrderPayload payload) {
        String orderNo = ProductType.HOMESTAY.getPrefix() + IdWorker.getIdStr();
        Order order = DataUtil.copy(context, Order.class);
        order.setState(OrderState.valueOf(context.getTo()));
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
        orderService.insert(order);
        return order;
    }


    @Override
    protected void next(HomestayOrderCreateContext context, HomestayOrderPayload payload, Order order) {
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
}
