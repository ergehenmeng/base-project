package com.eghm.service.business.handler.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.OrderCreateDTO;
import com.eghm.model.dto.ext.BaseProduct;
import com.eghm.service.business.*;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
@Service("ticketOrderCreateHandler")
@Slf4j
public class TicketOrderCreateHandler extends AbstractOrderCreateHandler<ScenicTicket> {

    private final ScenicTicketService scenicTicketService;

    private final TicketOrderService orderService;

    public TicketOrderCreateHandler(OrderService orderService, UserCouponService userCouponService, OrderVisitorService orderVisitorService, OrderMQService orderMQService, ScenicTicketService scenicTicketService, TicketOrderService orderService1) {
        super(orderService, userCouponService, orderVisitorService, orderMQService);
        this.scenicTicketService = scenicTicketService;
        this.orderService = orderService1;
    }

    @Override
    protected ScenicTicket getProduct(OrderCreateDTO dto) {
        return scenicTicketService.selectByIdShelve(dto.getProductId());
    }

    @Override
    protected BaseProduct getBaseProduct(OrderCreateDTO dto, ScenicTicket product) {
        BaseProduct copy = DataUtil.copy(product, BaseProduct.class);
        // 门票默认可以使用优惠券
        copy.setSupportedCoupon(true);
        copy.setHotSell(false);
        copy.setRefundDescribe("");
        return copy;
    }

    @Override
    protected void before(OrderCreateDTO dto, ScenicTicket ticket) {
        if (ticket.getStock() - dto.getNum() < 0) {
            log.error("门票库存不足 [{}] [{}] [{}]", ticket.getId(), ticket.getStock(), dto.getNum());
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
        if (ticket.getQuota() < dto.getNum()) {
            log.error("超出门片单次购买上限 [{}] [{}] [{}]", ticket.getId(), ticket.getQuota(), dto.getNum());
            throw new BusinessException(ErrorCode.TICKET_QUOTA.getCode(), String.format(ErrorCode.TICKET_QUOTA.getMsg(), ticket.getQuota()));
        }
        // 待补充用户信息
        if (Boolean.TRUE.equals(ticket.getRealBuy()) && (CollUtil.isEmpty(dto.getVisitorList()) || dto.getVisitorList().size() != dto.getNum())) {
            log.error("实名制购票录入游客信息不匹配 [{}]", ticket.getId());
            throw new BusinessException(ErrorCode.TICKET_VISITOR);
        }
    }

    @Override
    protected void next(OrderCreateDTO dto, ScenicTicket product, Order order) {

        TicketOrder ticketOrder = DataUtil.copy(product, TicketOrder.class);
        ticketOrder.setOrderNo(order.getOrderNo());
        ticketOrder.setMobile(dto.getMobile());
        ticketOrder.setTicketId(dto.getProductId());
        orderService.insert(ticketOrder);

        scenicTicketService.updateStock(dto.getProductId(), dto.getNum());
    }

}
