package com.eghm.service.business.impl;

import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.mapper.TicketOrderCombineMapper;
import com.eghm.model.ScenicTicket;
import com.eghm.model.TicketOrderCombine;
import com.eghm.service.business.TicketOrderCombineService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合票订单表 服务实现类
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
            TicketOrderCombine snapshot = DataUtil.copy(ticket, TicketOrderCombine.class, "id");
            snapshot.setOrderNo(orderNo);
            snapshot.setTicketId(ticket.getId());
            ticketOrderCombineMapper.insert(snapshot);
        });
    }
}
