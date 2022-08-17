package com.eghm.service.business.impl;

import com.eghm.dao.mapper.OrderMapper;
import com.eghm.service.business.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Service("orderService")
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

}
