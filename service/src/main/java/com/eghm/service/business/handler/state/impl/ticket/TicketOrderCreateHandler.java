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
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.TicketOrderCreateContext;
import com.eghm.service.business.handler.dto.TicketOrderPayload;
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
public class TicketOrderCreateHandler extends AbstractOrderCreateHandler<TicketOrderCreateContext, TicketOrderPayload> {

    private final ScenicTicketService scenicTicketService;

    private final ScenicService scenicService;

    private final TicketOrderService ticketOrderService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    public TicketOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, ScenicService scenicService, TicketOrderService ticketOrderService) {
        super(memberCouponService, orderVisitorService);
        this.scenicService = scenicService;
        this.scenicTicketService = scenicTicketService;
        this.ticketOrderService = ticketOrderService;
        this.orderService = orderService;
        this.orderMQService = orderMQService;
    }

    @Override
    protected TicketOrderPayload getPayload(TicketOrderCreateContext context) {
        ScenicTicket scenicTicket = scenicTicketService.selectByIdShelve(context.getTicketId());
        Scenic scenic = scenicService.selectByIdShelve(scenicTicket.getScenicId());
        TicketOrderPayload payload = new TicketOrderPayload();
        payload.setTicket(scenicTicket);
        payload.setScenic(scenic);
        return payload;
    }

    @Override
    protected void before(TicketOrderCreateContext context, TicketOrderPayload payload) {
        int num = context.getVisitorList().size();
        ScenicTicket ticket = payload.getTicket();
        if (ticket.getStock() - num < 0) {
            log.error("门票库存不足 [{}] [{}] [{}]", ticket.getId(), ticket.getStock(), num);
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
        if (ticket.getQuota() < num) {
            log.error("超出门票单次购买上限 [{}] [{}] [{}]", ticket.getId(), ticket.getQuota(), num);
            throw new BusinessException(ErrorCode.TICKET_QUOTA.getCode(), String.format(ErrorCode.TICKET_QUOTA.getMsg(), ticket.getQuota()));
        }
        // 待补充用户信息
        if (Boolean.TRUE.equals(ticket.getRealBuy()) && CollUtil.isEmpty(context.getVisitorList())) {
            log.error("实名制购票录入游客信息不匹配 [{}]", ticket.getId());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
        if (!context.getVisitorList().isEmpty() && context.getVisitorList().size() != context.getNum()) {
            log.error("实名制购票录入游客信息与数量不匹配 [{}] [{}] [{}]", ticket.getId(), context.getNum(), context.getVisitorList());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
    }

    @Override
    protected Order createOrder(TicketOrderCreateContext context, TicketOrderPayload payload) {
        ScenicTicket ticket = payload.getTicket();
        String orderNo = ProductType.TICKET.generateOrderNo();
        Order order = new Order();
        order.setMerchantId(payload.getScenic().getMerchantId());
        order.setMemberId(context.getMemberId());
        order.setTitle(ticket.getTitle());
        order.setState(OrderState.UN_PAY);
        order.setProductType(ProductType.TICKET);
        order.setCoverUrl(ticket.getCoverUrl());
        order.setOrderNo(orderNo);
        order.setRemark(context.getRemark());
        order.setPrice(ticket.getSalePrice());
        order.setNum(context.getNum());
        order.setPayAmount(context.getNum() * ticket.getSalePrice());
        order.setMultiple(false);
        order.setRefundType(ticket.getRefundType());
        order.setRefundDescribe(ticket.getRefundDescribe());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), ticket.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(TicketOrderCreateContext context, TicketOrderPayload payload) {
        return payload.getTicket().getHotSell();
    }

    @Override
    public void queueOrder(TicketOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.TICKET_ORDER, context);
    }

    @Override
    protected void next(TicketOrderCreateContext context, TicketOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        scenicTicketService.updateStock(payload.getTicket().getId(), -order.getNum());
        TicketOrder ticketOrder = DataUtil.copy(payload.getTicket(), TicketOrder.class, "id");
        ticketOrder.setOrderNo(order.getOrderNo());
        ticketOrder.setMobile(context.getMobile());
        ticketOrder.setTicketId(context.getTicketId());
        ticketOrder.setVisitDate(context.getVisitDate());
        ticketOrder.setScenicName(payload.getScenic().getScenicName());
        ticketOrderService.insert(ticketOrder);
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected void end(TicketOrderCreateContext context, TicketOrderPayload payload, Order order) {
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
