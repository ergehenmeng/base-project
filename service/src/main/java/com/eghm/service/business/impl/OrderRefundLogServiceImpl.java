package com.eghm.service.business.impl;

import com.eghm.dao.mapper.OrderRefundLogMapper;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/1
 */
@Service("orderRefundLogService")
@AllArgsConstructor
@Slf4j
public class OrderRefundLogServiceImpl implements OrderRefundLogService {

    private final OrderRefundLogMapper orderRefundLogMapper;

    @Override
    public void insert(OrderRefundLog log) {
        orderRefundLogMapper.insert(log);
    }
}
