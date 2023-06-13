package com.eghm.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.mapper.PayRequestLogMapper;
import com.eghm.model.PayRequestLog;
import com.eghm.service.pay.PayRequestLogService;
import com.eghm.service.common.JsonService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.StepType;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付或退款请求记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Service("payRequestLogService")
@AllArgsConstructor
public class PayRequestLogServiceImpl implements PayRequestLogService {

    private final PayRequestLogMapper payRequestLogMapper;

    private final JsonService jsonService;

    @Override
    public Page<PayRequestLog> getByPage(PayLogQueryRequest request) {
        LambdaQueryWrapper<PayRequestLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getPayChannel() != null, PayRequestLog::getPayChannel, request.getPayChannel());
        wrapper.eq(request.getStepType() != null, PayRequestLog::getStepType, request.getPayChannel());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper -> queryWrapper
                .like(PayRequestLog::getOrderNo, request.getQueryName())
                .or()
                .like(PayRequestLog::getOutTradeNo, request.getQueryName())
                .or()
                .like(PayRequestLog::getOutRefundNo, request.getQueryName()));
        return payRequestLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertPayLog(PrepayDTO request, PrepayVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getAttach());
        requestLog.setPayChannel(response.getPayChannel());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.PAY);
        requestLog.setOutTradeNo(request.getOutTradeNo());
        payRequestLogMapper.insert(requestLog);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertRefundLog(RefundDTO request, RefundVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getOrderNo());
        requestLog.setPayChannel(response.getPayChannel());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.REFUND);
        requestLog.setOutTradeNo(request.getOutTradeNo());
        requestLog.setOutRefundNo(request.getOutRefundNo());
        payRequestLogMapper.insert(requestLog);
    }
}
