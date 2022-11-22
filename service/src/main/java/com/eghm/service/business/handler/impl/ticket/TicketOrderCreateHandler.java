package com.eghm.service.business.handler.impl.ticket;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.StateMachineType;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.handler.dto.BaseProductDTO;
import com.eghm.service.business.handler.dto.OrderCreateContext;
import com.eghm.model.dto.ext.BaseProduct;
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

    private final TicketOrderService orderService;

    public TicketOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, TicketOrderService orderService1) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.scenicTicketService = scenicTicketService;
        this.orderService = orderService1;
    }

    @Override
    protected ScenicTicket getProduct(TicketOrderCreateContext context) {
        return scenicTicketService.selectByIdShelve(context.getTicketId());
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateContext dto, ScenicTicket product) {
        BaseProduct baseProduct = DataUtil.copy(product, BaseProduct.class);
        // 门票默认可以使用优惠券
        baseProduct.setSupportedCoupon(true);
        baseProduct.setDeliveryType(DeliveryType.NO_SHIPMENT);
        baseProduct.setHotSell(false);
        baseProduct.setMultiple(false);
        return baseProduct;
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
    protected void next(OrderCreateContext dto, ScenicTicket product, Order order) {
        BaseProductDTO base = dto.getFirstProduct();
        scenicTicketService.updateStock(base.getProductId(), -order.getNum());
        TicketOrder ticketOrder = DataUtil.copy(product, TicketOrder.class);
        ticketOrder.setOrderNo(order.getOrderNo());
        ticketOrder.setMobile(dto.getMobile());
        ticketOrder.setTicketId(base.getProductId());
        orderService.insert(ticketOrder);
    }



    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public StateMachineType getStateMachineType() {
        return null;
    }
}
