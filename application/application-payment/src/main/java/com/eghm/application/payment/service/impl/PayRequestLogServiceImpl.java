package com.eghm.application.payment.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.common.JsonService;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.domain.payment.enums.StepType;
import com.eghm.domain.payment.model.PayRequestLog;
import com.eghm.domain.payment.repository.PayRequestLogRepository;
import com.eghm.application.payment.service.PayRequestLogQueryGateway;
import com.eghm.application.payment.service.PayRequestLogService;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;
import com.eghm.vo.operate.log.PayRequestLogResponse;
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
@AllArgsConstructor
@Service("payRequestLogService")
public class PayRequestLogServiceImpl implements PayRequestLogService {

    private final JsonService jsonService;

    private final PayRequestLogRepository payRequestLogRepository;

    private final PayRequestLogQueryGateway payRequestLogQueryGateway;

    @Override
    public Page<PayRequestLogResponse> getByPage(PayLogQueryRequest request) {
        return payRequestLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertPayLog(PrepayDTO request, PrepayVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getOrderNo());
        requestLog.setPayChannel(request.getTradeType().getPayChannel());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.PAY);
        requestLog.setTradeNo(request.getTradeNo());
        payRequestLogRepository.save(requestLog);
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
        payRequestLogRepository.save(requestLog);
    }
}
