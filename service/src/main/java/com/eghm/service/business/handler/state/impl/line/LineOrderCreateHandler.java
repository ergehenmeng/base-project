package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Line;
import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.model.TravelAgency;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.LineOrderCreateContext;
import com.eghm.service.business.handler.dto.LineOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2022/9/2
 */
@Service("lineOrderCreateHandler")
@Slf4j
public class LineOrderCreateHandler extends AbstractOrderCreateHandler<LineOrderCreateContext, LineOrderPayload> {

    private final LineService lineService;

    private final TravelAgencyService travelAgencyService;

    private final LineConfigService lineConfigService;

    private final LineDayConfigService lineDayConfigService;

    private final LineOrderService lineOrderService;

    private final LineOrderSnapshotService lineOrderSnapshotService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public LineOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, TravelAgencyService travelAgencyService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineOrderSnapshotService lineOrderSnapshotService) {
        super(memberCouponService, orderVisitorService);
        this.lineService = lineService;
        this.travelAgencyService = travelAgencyService;
        this.lineConfigService = lineConfigService;
        this.lineDayConfigService = lineDayConfigService;
        this.lineOrderService = lineOrderService;
        this.lineOrderSnapshotService = lineOrderSnapshotService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected LineOrderPayload getPayload(LineOrderCreateContext context) {
        Line line = lineService.selectByIdShelve(context.getLineId());
        TravelAgency travelAgency = travelAgencyService.selectByIdShelve(line.getTravelAgencyId());
        LineOrderPayload payload = new LineOrderPayload();
        payload.setLine(line);
        payload.setTravelAgency(travelAgency);
        payload.setConfig(lineConfigService.getConfig(context.getLineId(), context.getConfigDate()));
        payload.setDayList(lineDayConfigService.getByLineId(context.getLineId()));
        return payload;
    }

    @Override
    protected void before(LineOrderCreateContext context, LineOrderPayload payload) {
        if (payload.getConfig() == null) {
            log.error("线路日配置信息未查询到 [{}] [{}]", context.getLineId(), context.getConfigDate());
            throw new BusinessException(ErrorCode.LINE_NULL);
        }
        Integer num = context.getNum();
        if (payload.getConfig().getStock() - num < 0) {
            log.error("线路库存不足 [{}] [{}] [{}]", payload.getConfig().getId(), payload.getConfig().getStock(), num);
            throw new BusinessException(ErrorCode.LINE_STOCK);
        }
        if (Boolean.FALSE.equals(payload.getConfig().getState())) {
            log.error("线路该日期不可预定 [{}] [{}]", context.getLineId(), context.getConfigDate());
            throw new BusinessException(ErrorCode.LINE_NOT_ORDER);
        }
    }

    @Override
    protected Order createOrder(LineOrderCreateContext context, LineOrderPayload payload) {
        Order order = new Order();
        order.setRefundType(payload.getLine().getRefundType());
        order.setRefundDescribe(payload.getLine().getRefundDescribe());
        order.setState(OrderState.UN_PAY);
        order.setProductType(ProductType.LINE);
        order.setMerchantId(payload.getTravelAgency().getMerchantId());
        order.setStoreId(payload.getTravelAgency().getId());
        order.setCoverUrl(payload.getLine().getCoverUrl());
        order.setOrderNo(ProductType.LINE.generateOrderNo());
        order.setTitle(payload.getLine().getTitle());
        order.setPrice(payload.getConfig().getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), context.getLineId());
        orderService.save(order);
        context.setOrderNo(order.getOrderNo());
        return order;
    }

    @Override
    public boolean isHotSell(LineOrderCreateContext context, LineOrderPayload payload) {
        return payload.getLine().getHotSell();
    }

    @Override
    protected void queueOrder(LineOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.LINE_ORDER, context);
    }

    @Override
    protected void next(LineOrderCreateContext context, LineOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        lineConfigService.updateStock(payload.getConfig().getId(), -order.getNum());
        LineOrder lineOrder = DataUtil.copy(payload.getLine(), LineOrder.class, "id");
        lineOrder.setLineId(payload.getLine().getId());
        lineOrder.setMemberId(context.getMemberId());
        lineOrder.setOrderNo(order.getOrderNo());
        lineOrder.setLineConfigId(payload.getConfig().getId());
        lineOrder.setLinePrice(payload.getConfig().getLinePrice());
        lineOrder.setSalePrice(payload.getConfig().getSalePrice());
        lineOrder.setVisitDate(payload.getConfig().getConfigDate());
        lineOrderService.insert(lineOrder);
        lineOrderSnapshotService.insert(payload.getLine().getId(), order.getOrderNo(), payload.getDayList());
    }

    @Override
    protected void end(LineOrderCreateContext context, LineOrderPayload payload, Order order) {
        orderMQService.sendOrderExpireMessage(ExchangeQueue.LINE_PAY_EXPIRE, order.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
