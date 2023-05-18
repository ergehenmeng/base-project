package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.LineOrder;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.LineOrderCreateContext;
import com.eghm.service.business.handler.dto.LineOrderPayload;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/2
 */
@Service("lineOrderCreateHandler")
@Slf4j
public class LineOrderCreateHandler extends AbstractOrderCreateHandler<LineOrderCreateContext, LineOrderPayload> {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    private final LineDayConfigService lineDayConfigService;

    private final LineOrderService lineOrderService;

    private final LineDaySnapshotService lineDaySnapshotService;

    private final OrderService orderService;

    public LineOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, LineService lineService, LineConfigService lineConfigService, LineDayConfigService lineDayConfigService, LineOrderService lineOrderService, LineDaySnapshotService lineDaySnapshotService) {
        super(userCouponService, orderVisitorService, orderMQService);
        this.lineService = lineService;
        this.lineConfigService = lineConfigService;
        this.lineDayConfigService = lineDayConfigService;
        this.lineOrderService = lineOrderService;
        this.lineDaySnapshotService = lineDaySnapshotService;
        this.orderService = orderService;
    }

    @Override
    protected LineOrderPayload getPayload(LineOrderCreateContext context) {
        LineOrderPayload payload = new LineOrderPayload();
        payload.setLine(lineService.selectByIdShelve(context.getLineId()));
        payload.setConfig(lineConfigService.getConfig(context.getLineId(), context.getConfigDate()));
        payload.setDayList(lineDayConfigService.getByLineId(context.getLineId()));
        return payload;
    }

    @Override
    protected void before(LineOrderCreateContext context, LineOrderPayload payload) {
        Integer num = context.getNum();
        if (payload.getConfig().getStock() - num < 0) {
            log.error("线路库存不足 [{}] [{}] [{}]", payload.getConfig().getId(), payload.getConfig().getStock(), num);
            throw new BusinessException(ErrorCode.LINE_STOCK);
        }
    }

    @Override
    protected Order createOrder(LineOrderCreateContext context, LineOrderPayload payload) {
        String orderNo = ProductType.LINE.generateOrderNo();
        Order order = DataUtil.copy(context, Order.class);
        order.setState(OrderState.UN_PAY);
        order.setUserId(context.getUserId());
        order.setOrderNo(orderNo);
        order.setNum(context.getNum());
        order.setTitle(payload.getLine().getTitle());
        order.setPrice(payload.getConfig().getSalePrice());
        order.setPayAmount(order.getNum() * order.getPrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        // 使用优惠券
        this.useDiscount(order, context.getUserId(), context.getCouponId(), context.getLineId());
        orderService.save(order);
        return order;
    }

    @Override
    protected void next(LineOrderCreateContext context, LineOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        lineConfigService.updateStock(payload.getConfig().getId(), -order.getNum());
        LineOrder lineOrder = DataUtil.copy(payload.getLine(), LineOrder.class);
        lineOrder.setOrderNo(order.getOrderNo());
        lineOrder.setLineConfigId(payload.getConfig().getId());
        lineOrder.setLinePrice(payload.getConfig().getLinePrice());
        lineOrder.setMobile(context.getMobile());
        lineOrder.setNickName(context.getNickName());
        lineOrderService.insert(lineOrder);
        lineDaySnapshotService.insert(payload.getLine().getId(), order.getOrderNo(), payload.getDayList());
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
