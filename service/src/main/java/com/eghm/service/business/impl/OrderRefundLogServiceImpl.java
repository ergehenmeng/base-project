package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.dto.ext.OrderRefund;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderRefundLogMapper;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.vo.business.order.refund.RefundLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/1
 */
@Service("orderRefundLogService")
@AllArgsConstructor
@Slf4j
public class OrderRefundLogServiceImpl implements OrderRefundLogService {

    private final OrderRefundLogMapper orderRefundLogMapper;

    @Override
    public Page<RefundLogResponse> getByPage(RefundLogQueryRequest request) {
        return orderRefundLogMapper.getByPage(request.createPage(), request);
    }

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
    public int getTotalRefundNum(String orderNo, Long itemOrderId) {
        return orderRefundLogMapper.getTotalRefundNum(orderNo, itemOrderId);
    }

    @Override
    public int getRefundSuccessNum(String orderNo, Long itemOrderId) {
        return orderRefundLogMapper.getRefundSuccessNum(orderNo, itemOrderId);
    }

    @Override
    public OrderRefundLog selectByOutRefundNo(String outRefundNo) {
        LambdaQueryWrapper<OrderRefundLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderRefundLog::getOutRefundNo, outRefundNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return orderRefundLogMapper.selectOne(wrapper);
    }

    @Override
    public List<OrderRefund> getRefundProcess() {
        return orderRefundLogMapper.getRefundProcess();
    }

    @Override
    public boolean hasRefundSuccess(String orderNo, List<Long> visitorList) {
        int refundSuccess = orderRefundLogMapper.getRefundSuccess(orderNo, visitorList);
        return refundSuccess > 0;
    }
}
