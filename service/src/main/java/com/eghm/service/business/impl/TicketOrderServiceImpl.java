package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.TicketOrderMapper;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;
import com.eghm.service.business.*;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Service("ticketOrderService")
@AllArgsConstructor
@Slf4j
public class TicketOrderServiceImpl implements TicketOrderService {

    private final TicketOrderMapper ticketOrderMapper;

    private final ScenicTicketService scenicTicketService;

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderHandlerService;

    private final OrderService orderService;

    @Override
    public void create(CreateTicketOrderDTO dto) {
        ScenicTicket ticket = scenicTicketService.selectByIdShelve(dto.getTicketId());
        this.checkTicket(ticket, dto);
        String orderNo = ProductType.TICKET.getPrefix() + IdWorker.getIdStr();
        Order order = DataUtil.copy(dto, Order.class);
        order.setTitle(ticket.getTitle());
        order.setOrderNo(orderNo);
        order.setPrice(ticket.getSalePrice());
        order.setProductType(ProductType.TICKET);
        order.setRefundType(ticket.getRefundType());
        order.setPayAmount(dto.getNum() * ticket.getSalePrice());

        if (dto.getCouponId() != null) {
            Integer couponAmount = userCouponService.getCouponAmountWithVerify(dto.getUserId(), dto.getCouponId(), dto.getTicketId(), order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setCouponId(dto.getCouponId());
            userCouponService.useCoupon(dto.getCouponId());
        }
        // 添加主订单
        orderService.insert(order);
        // 添加门票订单 TODO 订单总金额为零时 要做特殊处理
        TicketOrder ticketOrder = DataUtil.copy(ticket, TicketOrder.class);
        ticketOrder.setOrderNo(orderNo);
        ticketOrder.setMobile(dto.getMobile());
        ticketOrder.setTicketId(dto.getTicketId());
        ticketOrderMapper.insert(ticketOrder);
        // 门票订单关联购票人信息
        orderVisitorService.addVisitor(ProductType.TICKET, ticketOrder.getOrderNo(), dto.getVisitorList());
        // 库存更新
        scenicTicketService.updateStock(ticket.getId(), dto.getNum());
        // 30分钟过期开启mq
        orderHandlerService.sendOrderExpireMessage(ticketOrder.getOrderNo());
    }

    @Override
    public TicketOrder selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return ticketOrderMapper.selectOne(wrapper);
    }

    /**
     * 校验门票是否符合购买条件
     * @param ticket 门票信息
     * @param dto 购买信息
     */
    private void checkTicket(ScenicTicket ticket, CreateTicketOrderDTO dto) {
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

}
