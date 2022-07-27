package com.eghm.service.business.impl;

import com.eghm.dao.mapper.TicketOrderMapper;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;
import com.eghm.service.business.TicketOrderService;
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

    @Override
    public void create(CreateTicketOrderDTO dto) {

    }
}
