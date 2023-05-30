package com.eghm.service.business.handler.state.impl.ticket;

import cn.hutool.core.collection.CollUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.TicketOrderCreateContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
@Service("ticketOrderCreateHandler")
@Slf4j
public class TicketOrderCreateHandler extends AbstractOrderCreateHandler<TicketOrderCreateContext, ScenicTicket> {

    private final ScenicTicketService scenicTicketService;

    private final TicketOrderService ticketOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public TicketOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, TicketOrderService ticketOrderService) {
        super(userCouponService, orderVisitorService);
        this.scenicTicketService = scenicTicketService;
        this.ticketOrderService = ticketOrderService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected ScenicTicket getPayload(TicketOrderCreateContext context) {
        return scenicTicketService.selectByIdShelve(context.getTicketId());
    }


    @Override
    protected void before(TicketOrderCreateContext context, ScenicTicket ticket) {
        int num = context.getVisitorList().size();
        if (ticket.getStock() - num < 0) {
            log.error("门票库存不足 [{}] [{}] [{}]", ticket.getId(), ticket.getStock(), num);
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
        if (ticket.getQuota() < num) {
            log.error("超出门票单次购买上限 [{}] [{}] [{}]", ticket.getId(), ticket.getQuota(), num);
            throw new BusinessException(ErrorCode.TICKET_QUOTA.getCode(), String.format(ErrorCode.TICKET_QUOTA.getMsg(), ticket.getQuota()));
        }
        // 待补充用户信息
        if (Boolean.TRUE.equals(ticket.getRealBuy()) && (CollUtil.isEmpty(context.getVisitorList()) || context.getVisitorList().size() != num)) {
            log.error("实名制购票录入游客信息不匹配 [{}]", ticket.getId());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
    }

    @Override
    protected Order createOrder(TicketOrderCreateContext context, ScenicTicket payload) {
        String orderNo = ProductType.TICKET.generateTradeNo();
        Order order = new Order();
        order.setUserId(context.getUserId());
        order.setTitle(payload.getTitle());
        order.setState(OrderState.UN_PAY);
        order.setProductType(ProductType.TICKET);
        order.setOrderNo(orderNo);
        order.setPrice(payload.getSalePrice());
        order.setNum(context.getVisitorList().size());
        order.setPayAmount(order.getNum() * payload.getSalePrice());

        order.setMultiple(false);
        order.setRefundType(payload.getRefundType());
        order.setRefundDescribe(payload.getRefundDescribe());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        // 使用优惠券
        this.useDiscount(order, context.getUserId(), context.getCouponId(), payload.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(TicketOrderCreateContext context, ScenicTicket payload) {
        return payload.getHotSell();
    }

    @Override
    public void queueOrder(TicketOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.TICKET_ORDER, context);
    }

    @Override
    protected void next(TicketOrderCreateContext context, ScenicTicket payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        scenicTicketService.updateStock(payload.getId(), -order.getNum());
        TicketOrder ticketOrder = DataUtil.copy(payload, TicketOrder.class);
        ticketOrder.setOrderNo(order.getOrderNo());
        ticketOrder.setMobile(context.getMobile());
        ticketOrder.setTicketId(context.getTicketId());
        ticketOrderService.insert(ticketOrder);
    }

    @Override
    protected void end(TicketOrderCreateContext context, ScenicTicket payload, Order order) {
        orderMQService.sendOrderExpireMessage(ExchangeQueue.TICKET_PAY_EXPIRE, order.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
