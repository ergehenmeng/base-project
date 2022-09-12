package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.OrderRefundLogMapper;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.model.dto.ext.OrderRefund;
import com.eghm.service.business.OrderRefundLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int getTotalRefundNum(String orderNo) {
        return orderRefundLogMapper.getTotalRefundNum(orderNo, null);
    }

    @Override
    public int getTotalRefundNum(String orderNo, Long productOrderId) {
        return orderRefundLogMapper.getTotalRefundNum(orderNo, null);
    }

    @Override
    public OrderRefundLog selectByOutRefundNo(String outRefundNo) {
        LambdaQueryWrapper<OrderRefundLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderRefundLog::getOutRefundNo, outRefundNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return orderRefundLogMapper.selectOne(wrapper);
    }

    @Override
    public List<OrderRefund> getTicketRefunding() {
        return orderRefundLogMapper.getTicketRefunding();
    }
}
