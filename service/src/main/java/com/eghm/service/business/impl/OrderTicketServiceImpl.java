package com.eghm.service.business.impl;

import com.eghm.dao.mapper.OrderTicketMapper;
import com.eghm.service.business.OrderTicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
@Service("orderTicketService")
@AllArgsConstructor
@Slf4j
public class OrderTicketServiceImpl implements OrderTicketService {

    private final OrderTicketMapper orderTicketMapper;

}
