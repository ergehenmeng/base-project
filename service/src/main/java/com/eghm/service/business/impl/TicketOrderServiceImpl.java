package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CloseType;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.TicketOrderMapper;
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
public class TicketOrderServiceImpl implements TicketOrderService, OrderService {

    private final TicketOrderMapper ticketOrderMapper;

    private final ScenicTicketService scenicTicketService;

    private final UserCouponService userCouponService;

    private final OrderVisitorService orderVisitorService;

    private final OrderMQService orderHandlerService;

    @Override
    public void create(CreateTicketOrderDTO dto) {
        ScenicTicket ticket = scenicTicketService.selectByIdShelve(dto.getTicketId());
        this.checkTicket(ticket, dto);

        TicketOrder order = DataUtil.copy(ticket, TicketOrder.class);
        order.setOrderNo(ProductType.TICKET.getPrefix() + IdWorker.getIdStr());
        order.setMobile(dto.getMobile());
        order.setTicketId(dto.getTicketId());
        order.setUserId(dto.getUserId());
        order.setPayAmount(dto.getNum() * ticket.getSalePrice());

        if (dto.getCouponId() != null) {
            Integer couponAmount = userCouponService.getCouponAmountWithVerify(dto.getUserId(), dto.getCouponId(), order.getPayAmount());
            order.setPayAmount(order.getPayAmount() - couponAmount);
            order.setCouponId(dto.getCouponId());
            userCouponService.useCoupon(dto.getCouponId());
        }
        ticketOrderMapper.insert(order);
        orderVisitorService.addVisitor(ProductType.TICKET, order.getId(), dto.getVisitorList());
        scenicTicketService.updateStock(ticket.getId(), dto.getNum());
        orderHandlerService.sendOrderExpireMessage(order.getOrderNo());
    }

    @Override
    public TicketOrder selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOrderNo, orderNo);
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

    @Override
    public void orderExpireHandler(String orderNo) {
        TicketOrder ticketOrder = this.selectByOrderNo(orderNo);
        if (ticketOrder == null) {
            log.error("门票订单已被删除 [{}]", orderNo);
            return;
        }
        if (ticketOrder.getState() != OrderState.UN_PAY) {
            log.error("门票订单状态不是待支付, 无需处理 [{}] [{}]", orderNo, ticketOrder.getState());
            return;
        }
        ticketOrder.setState(OrderState.CLOSE);
        ticketOrder.setCloseType(CloseType.EXPIRE);

    }
}
