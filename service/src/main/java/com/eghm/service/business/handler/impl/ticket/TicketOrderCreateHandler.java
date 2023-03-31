package com.eghm.service.business.handler.impl.ticket;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.dto.TicketOrderCreateContext;
import com.eghm.service.business.handler.impl.AbstractOrderCreateHandler;
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

    public TicketOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, TicketOrderService ticketOrderService) {
        super(userCouponService, orderVisitorService, orderMQService);
        this.scenicTicketService = scenicTicketService;
        this.ticketOrderService = ticketOrderService;
        this.orderService = orderService;
    }

    @Override
    protected ScenicTicket getPayload(TicketOrderCreateContext context) {
        return scenicTicketService.selectByIdShelve(context.getTicketId());
    }

    @Override
    protected Order createOrder(TicketOrderCreateContext context, ScenicTicket payload) {
        String orderNo = ProductType.TICKET.getPrefix() + IdWorker.getIdStr();
        // TODO 待完善
        Order order = DataUtil.copy(context, Order.class);
        order.setState(OrderState.of(context.getTo()));
        order.setUserId(context.getUserId());
        order.setOrderNo(orderNo);
        order.setPrice(payload.getSalePrice());
        order.setPayAmount(order.getNum() * payload.getSalePrice());
        order.setDeliveryType(DeliveryType.NO_SHIPMENT);
        order.setMultiple(false);
        // 使用优惠券
        this.useDiscount(order, context.getUserId(), context.getCouponId(), payload.getId());
        orderService.insert(order);
        return order;
    }

    @Override
    protected void before(TicketOrderCreateContext dto, ScenicTicket ticket) {
        Integer num = dto.getNum();
        if (ticket.getStock() - num < 0) {
            log.error("门票库存不足 [{}] [{}] [{}]", ticket.getId(), ticket.getStock(), num);
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
        if (ticket.getQuota() < num) {
            log.error("超出门票单次购买上限 [{}] [{}] [{}]", ticket.getId(), ticket.getQuota(), num);
            throw new BusinessException(ErrorCode.TICKET_QUOTA.getCode(), String.format(ErrorCode.TICKET_QUOTA.getMsg(), ticket.getQuota()));
        }
        // 待补充用户信息
        if (Boolean.TRUE.equals(ticket.getRealBuy()) && (CollUtil.isEmpty(dto.getVisitorList()) || dto.getVisitorList().size() != num)) {
            log.error("实名制购票录入游客信息不匹配 [{}]", ticket.getId());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
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
    public boolean isHotSell(TicketOrderCreateContext context, ScenicTicket payload) {
        // TODO 热销商品设置
        return true;
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
