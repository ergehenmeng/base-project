package com.eghm.service.business.impl;

import com.eghm.dao.mapper.HomestayOrderMapper;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Slf4j
@Service("homestayOrderService")
@AllArgsConstructor
public class HomestayOrderServiceImpl implements HomestayOrderService {

    private final HomestayOrderMapper homestayOrderMapper;

    private final OrderService orderService;

}
