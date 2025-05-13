package com.eghm.state.machine.impl.ticket;

import com.eghm.common.JsonService;
import com.eghm.common.OrderMqService;
import com.eghm.enums.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.TicketOrder;
import com.eghm.model.TicketOrderCombine;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.state.machine.impl.AbstractOrderVerifyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Slf4j
@Service("ticketOrderVerifyHandler")
public class TicketOrderVerifyHandler extends AbstractOrderVerifyHandler {

    private final OrderMqService orderMqService;

    private final TicketOrderService ticketOrderService;

    private final TicketOrderCombineService ticketOrderCombineService;

    public TicketOrderVerifyHandler(OrderVisitorService orderVisitorService, OrderService orderService, VerifyLogService verifyLogService,
                                    JsonService jsonService, OrderMqService orderMqService, CommonService commonService,
                                    TicketOrderService ticketOrderService, TicketOrderCombineService ticketOrderCombineService) {
        super(jsonService, orderService, commonService, verifyLogService, orderVisitorService);
        this.orderMqService = orderMqService;
        this.ticketOrderService = ticketOrderService;
        this.ticketOrderCombineService = ticketOrderCombineService;
    }

    @Override
    protected void calcOrderState(OrderVerifyContext context, Order order) {
        TicketOrder ticketOrder = ticketOrderService.getByOrderNo(context.getOrderNo());
        if (ticketOrder.getCategory() != TicketType.COMBINE) {
            super.calcOrderState(context, order);
        } else {
            List<TicketOrderCombine> combineList = ticketOrderCombineService.getByOrderNo(order.getOrderNo());
            LocalDateTime now = LocalDateTime.now();
            Optional<TicketOrderCombine> optional = combineList.stream().filter(combine -> combine.getId().equals(context.getCombineId())).findFirst();
            if (optional.isEmpty()) {
                log.error("套票订单未查询 [{}] [{}]", context.getOrderNo(), context.getCombineId());
                throw new BusinessException(ErrorCode.COMBINE_ORDER_NULL);
            }
            TicketOrderCombine orderCombine = optional.get();
            if (orderCombine.getUseTime() != null) {
                log.error("组合订单重复核销 [{}] [{}]", context.getOrderNo(), context.getCombineId());
                throw new BusinessException(ErrorCode.COMBINE_ORDER_REDO_VERIFY);
            }
            orderCombine.setUseTime(now);
            ticketOrderCombineService.updateById(orderCombine);
            boolean match = combineList.stream().anyMatch(combine -> combine.getUseTime() == null);
            if (!match) {
                order.setCompleteTime(now);
                order.setState(OrderState.COMPLETE);
            }
        }
        if (order.getState() == OrderState.COMPLETE) {
            ticketOrder.setUseTime(LocalDateTime.now());
            ticketOrderService.updateById(ticketOrder);
        }
        context.setCategory(ticketOrder.getCategory());
    }

    @Override
    protected int tryVerifyVisitor(OrderVerifyContext context, Order order, Long verifyId) {
        // 组合票只有最后一张核销后才会核销游客信息, 且由于组合票核销时单次只能核销1张, 此处返回1
        if (context.getCategory() == TicketType.COMBINE && order.getState() != OrderState.COMPLETE) {
            return 1;
        } else {
            return super.tryVerifyVisitor(context, order, verifyId);
        }
    }

    @Override
    protected void end(OrderVerifyContext context, Order order) {
        orderMqService.sendOrderCompleteMessage(ExchangeQueue.TICKET_COMPLETE_DELAY, context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.VERIFY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
