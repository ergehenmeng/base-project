package com.eghm.state.machine.impl.ticket;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.OrderMQService;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.TicketType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.*;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.access.impl.TicketAccessHandler;
import com.eghm.state.machine.context.TicketOrderCreateContext;
import com.eghm.state.machine.dto.TicketOrderPayload;
import com.eghm.state.machine.impl.AbstractOrderCreateHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/8/21
 */
@Service("ticketOrderCreateHandler")
@Slf4j
public class TicketOrderCreateHandler extends AbstractOrderCreateHandler<TicketOrderCreateContext, TicketOrderPayload> {

    private final ScenicTicketService scenicTicketService;

    private final ScenicService scenicService;

    private final TicketOrderService ticketOrderService;

    private final OrderService orderService;

    private final TicketOrderSnapshotService ticketOrderSnapshotService;

    public TicketOrderCreateHandler(OrderService orderService, MemberCouponService memberCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService,
                                    ScenicService scenicService, TicketOrderService ticketOrderService, RedeemCodeGrantService redeemCodeGrantService, TicketOrderSnapshotService ticketOrderSnapshotService) {
        super(orderMQService, memberCouponService, orderVisitorService, redeemCodeGrantService);
        this.scenicService = scenicService;
        this.scenicTicketService = scenicTicketService;
        this.ticketOrderService = ticketOrderService;
        this.orderService = orderService;
        this.ticketOrderSnapshotService = ticketOrderSnapshotService;
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
            throw new BusinessException(ErrorCode.TICKET_QUOTA, ticket.getQuota());
        }
        Integer advanceDay = payload.getTicket().getAdvanceDay();
        LocalDate canVisitDate = LocalDate.now().plusDays(advanceDay);
        if (context.getVisitDate().isBefore(canVisitDate)) {
            log.error("门票不可预定该日期,需提前购买 [{}] [{}]", context.getTicketId(), context.getVisitDate());
            throw new BusinessException(ErrorCode.TICKET_ADVANCE_DAY, advanceDay);
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
        Order order = new Order();
        order.setMerchantId(payload.getScenic().getMerchantId());
        order.setStoreId(payload.getScenic().getId());
        order.setTitle(ticket.getTitle());
        order.setMemberId(context.getMemberId());
        order.setState(OrderState.UN_PAY);
        order.setProductType(ProductType.TICKET);
        order.setCoverUrl(payload.getScenic().getCoverUrl());
        order.setOrderNo(ProductType.TICKET.generateOrderNo());
        order.setTradeNo(ProductType.TICKET.generateTradeNo());
        order.setNum(context.getNum());
        order.setMobile(context.getMobile());
        order.setRemark(context.getRemark());
        // 实名制购票,默认第一个作为订单人
        if (CollUtil.isNotEmpty(context.getVisitorList())) {
            order.setNickName(context.getVisitorList().get(0).getMemberName());
        }
        order.setPrice(ticket.getSalePrice());
        order.setAmount(context.getNum() * order.getPrice());
        order.setPayAmount(context.getNum() * order.getPrice());
        order.setMultiple(false);
        order.setRefundType(RefundType.DIRECT_REFUND);
        order.setCreateDate(LocalDate.now());
        order.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        order.setCreateTime(LocalDateTime.now());
        // 使用优惠券
        this.useDiscount(order, context.getMemberId(), context.getCouponId(), ticket.getId());
        orderService.save(order);
        return order;
    }

    @Override
    public boolean isHotSell(TicketOrderPayload payload) {
        return payload.getTicket().getHotSell();
    }

    @Override
    protected void next(TicketOrderCreateContext context, TicketOrderPayload payload, Order order) {
        super.addVisitor(order, context.getVisitorList());
        scenicTicketService.updateStock(payload.getTicket().getId(), -order.getNum());
        TicketOrder ticketOrder = DataUtil.copy(payload.getTicket(), TicketOrder.class, "id");
        ticketOrder.setOrderNo(order.getOrderNo());
        ticketOrder.setTicketId(context.getTicketId());
        ticketOrder.setVisitDate(context.getVisitDate());
        ticketOrder.setScenicName(payload.getScenic().getScenicName());
        ticketOrderService.insert(ticketOrder);
        if (payload.getTicket().getCategory() == TicketType.COMBINE) {
            ticketOrderSnapshotService.insert(order.getOrderNo(), payload.getTicket().getId());
        }
        context.setOrderNo(order.getOrderNo());
    }

    @Override
    protected AbstractAccessHandler getAccessHandler() {
        return SpringContextUtil.getBean(TicketAccessHandler.class);
    }

    @Override
    protected ExchangeQueue getExchangeQueue() {
        return ExchangeQueue.TICKET_PAY_EXPIRE;
    }

    @Override
    protected Integer getLowestAmount() {
        return 0;
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
