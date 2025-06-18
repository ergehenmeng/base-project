package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.mapper.TicketOrderCombineMapper;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrderCombine;
import com.eghm.service.business.TicketOrderCombineService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ticket.CombineOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 套票票订单表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class TicketOrderCombineServiceImpl implements TicketOrderCombineService {

    private final ScenicTicketMapper scenicTicketMapper;

    private final TicketOrderCombineMapper ticketOrderCombineMapper;

    @Override
    public void insert(String orderNo, Long ticketId) {
        List<ScenicTicket> ticketList = scenicTicketMapper.getCombineList(ticketId);
        ticketList.forEach(ticket -> {
            TicketOrderCombine combine = DataUtil.copy(ticket, TicketOrderCombine.class, "id");
            combine.setOrderNo(orderNo);
            combine.setTicketId(ticket.getId());
            ticketOrderCombineMapper.insert(combine);
        });
    }

    @Override
    public List<TicketOrderCombine> getByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrderCombine> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrderCombine::getOrderNo, orderNo);
        return ticketOrderCombineMapper.selectList(wrapper);
    }

    @Override
    public void updateById(TicketOrderCombine combine) {
        ticketOrderCombineMapper.updateById(combine);
    }

    @Override
    public List<CombineOrderResponse> getCombineList(String orderNo) {
        return ticketOrderCombineMapper.getCombineList(orderNo);
    }
}
