package com.eghm.state.machine.impl.item;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.exception.BusinessException;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Slf4j
@Service("itemOrderVerifyHandler")
public class ItemOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    private final ItemOrderService itemOrderService;

    public ItemOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService,
                                  JsonService jsonService, OrderMqService orderMqService, CommonService commonService, ItemOrderService itemOrderService) {
        super(jsonService, orderService, commonService, verifyLogService, orderVisitorService);
        this.orderMqService = orderMqService;
        this.itemOrderService = itemOrderService;
    }

    @Override
    protected void before(OrderVerifyContext context, Order order) {
        super.before(context, order);
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(context.getOrderNo());
        List<Long> ids = orderList.stream().filter(itemOrder -> itemOrder.getRefundState() == ItemRefundState.INIT && itemOrder.getDeliveryState() == DeliveryState.PICK_UP).map(ItemOrder::getId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(context.getIds())) {
            boolean anyMatch = context.getIds().stream().noneMatch(ids::contains);
            if (anyMatch) {
                log.error("核销订单中存在非待自提的订单 [{}]", context.getIds());
                throw new BusinessException(ErrorCode.ORDER_NOT_PICK_UP);
            }
        }
    }

    @Override
    protected void calcOrderState(OrderVerifyContext context, Order order) {
        if (CollUtil.isEmpty(context.getIds()) || itemOrderService.getUnVerify(context.getOrderNo(), context.getIds()) <= 0) {
            order.setCompleteTime(LocalDateTime.now());
            order.setState(OrderState.COMPLETE);
        }
    }

    @Override
    protected int tryVerifyVisitor(OrderVerifyContext context, Order order, Long verifyId) {
        return itemOrderService.verify(context.getOrderNo(), context.getIds());
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMqService.sendOrderCompleteMessage(ExchangeQueue.ITEM_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
