package com.eghm.service.business.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
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

    @Override
    public OrderRefundLog selectById(Long id) {
        return orderRefundLogMapper.selectById(id);
    }

    @Override
    public OrderRefundLog selectByIdRequired(Long id) {
        OrderRefundLog select = orderRefundLogMapper.selectById(id);
        if (select == null) {
            log.error("未查询到退款记录 [{}]", id);
            throw new BusinessException(ErrorCode.REFUND_NOT_FOUND);
        }
        return select;
    }

    @Override
    public int updateById(OrderRefundLog log) {
        return orderRefundLogMapper.updateById(log);
    }

    @Override
    public int getTotalRefundAmount(Long orderId) {
        return 0;
    }
}
