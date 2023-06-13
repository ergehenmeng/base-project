package com.eghm.service.business.impl;

import com.eghm.mapper.PayRequestLogMapper;
import com.eghm.model.PayRequestLog;
import com.eghm.service.business.PayRequestLogService;
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

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertPayLog(PrepayDTO request, PrepayVO response) {
        PayRequestLog requestLog = new PayRequestLog();
        requestLog.setOrderNo(request.getAttach());
        requestLog.setTradeType(response.getTradeType().name());
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
        requestLog.setTradeType(response.getTradeType().name());
        requestLog.setRequestBody(jsonService.toJson(request));
        requestLog.setResponseBody(jsonService.toJson(response));
        requestLog.setStepType(StepType.REFUND);
        requestLog.setOutTradeNo(request.getOutTradeNo());
        requestLog.setOutRefundNo(request.getOutRefundNo());
        payRequestLogMapper.insert(requestLog);
    }
}
