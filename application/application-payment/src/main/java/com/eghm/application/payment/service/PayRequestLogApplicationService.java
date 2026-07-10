package com.eghm.application.payment.service;

import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;
import com.eghm.domain.payment.enums.StepType;
import com.eghm.domain.payment.model.PayRequestLog;
import com.eghm.domain.payment.repository.PayRequestLogRepository;
import com.eghm.domain.shared.service.JsonService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付或退款请求记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Service
@AllArgsConstructor
public class PayRequestLogApplicationService {
    
    private final JsonService jsonService;
    
    private final PayRequestLogRepository payRequestLogRepository;
    
    /**
     * 添加请求支付的日志
     *
     * @param request  请求参数
     * @param response 响应参数
     */
    @Async
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
    
    /**
     * 添加请求退款的日志
     *
     * @param request  请求参数
     * @param response 响应参数
     */
    @Async
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
    }}
