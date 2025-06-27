package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.mapper.OrderVerifyLogMapper;
import com.eghm.model.OrderVerifyLog;
import com.eghm.service.business.OrderVerifyLogService;
import com.eghm.vo.business.verify.OrderVerifyLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/6
 */
@Service("orderVerifyLogService")
@AllArgsConstructor
@Slf4j
public class OrderVerifyLogServiceImpl implements OrderVerifyLogService {

    private final OrderVerifyLogMapper orderVerifyLogMapper;

    @Override
    public Page<OrderVerifyLogResponse> getByPage(VerifyLogQueryRequest request) {
        return orderVerifyLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<OrderVerifyLogResponse> getList(VerifyLogQueryRequest request) {
        return orderVerifyLogMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public int getVerifiedNum(String orderNo) {
        return orderVerifyLogMapper.getVerifiedNum(orderNo);
    }

    @Override
    public void insert(OrderVerifyLog orderVerifyLog) {
        orderVerifyLogMapper.insert(orderVerifyLog);
    }
}
