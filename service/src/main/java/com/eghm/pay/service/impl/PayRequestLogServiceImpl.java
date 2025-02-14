package com.eghm.pay.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.JsonService;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.mapper.PayRequestLogMapper;
import com.eghm.model.PayRequestLog;
import com.eghm.pay.service.PayRequestLogService;
import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.dto.RefundDTO;
import com.eghm.pay.enums.StepType;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.pay.vo.RefundVO;
import com.eghm.vo.business.log.PayRequestLogResponse;
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

    private final JsonService jsonService;

    private final PayRequestLogMapper payRequestLogMapper;

    @Override
    public Page<PayRequestLogResponse> getByPage(PayLogQueryRequest request) {
        return payRequestLogMapper.getByPage(request.createPage(), request);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertPayLog(PrepayDTO request, PrepayVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getAttach());
        requestLog.setPayChannel(request.getTradeType().getPayChannel());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.PAY);
        requestLog.setTradeNo(request.getTradeNo());
        payRequestLogMapper.insert(requestLog);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertRefundLog(RefundDTO request, RefundVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getOrderNo());
        requestLog.setPayChannel(request.getTradeType().getPayChannel());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.REFUND);
        requestLog.setTradeNo(request.getTradeNo());
        requestLog.setRefundNo(request.getRefundNo());
        payRequestLogMapper.insert(requestLog);
    }
}
