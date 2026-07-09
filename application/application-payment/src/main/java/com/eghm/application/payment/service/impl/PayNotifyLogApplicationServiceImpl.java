package com.eghm.application.payment.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.pay.PayLogQueryRequest;
import com.eghm.application.payment.dto.PayNotifyMessage;
import com.eghm.domain.payment.enums.PayChannel;
import com.eghm.domain.payment.enums.StepType;
import com.eghm.domain.payment.model.PayNotifyLog;
import com.eghm.domain.payment.repository.PayNotifyLogRepository;
import com.eghm.application.payment.query.PayNotifyLogQueryService;
import com.eghm.application.payment.service.PayNotifyLogApplicationService;
import com.eghm.application.shared.vo.operate.log.PayNotifyLogResponse;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/26
 */
@AllArgsConstructor
@Service("payNotifyLogService")
public class PayNotifyLogApplicationServiceImpl implements PayNotifyLogApplicationService {

    private final PayNotifyLogRepository payNotifyLogRepository;

    private final PayNotifyLogQueryService payNotifyLogQueryGateway;

    @Override
    public Page<PayNotifyLogResponse> getByPage(PayLogQueryRequest request) {
        return payNotifyLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertAliLog(Map<String, String> params, StepType stepType) {
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(stepType);
        log.setPayChannel(PayChannel.ALIPAY);
        log.setParams(new Gson().toJson(params));
        log.setTradeNo(params.get("out_trade_no"));
        log.setNotifyId(params.get("notify_id"));
        log.setRefundNo(params.get("out_biz_no"));
        payNotifyLogRepository.save(log);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertWechatPayLog(PayNotifyMessage message) {
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(StepType.PAY);
        log.setPayChannel(PayChannel.WECHAT);
        log.setParams(message.getParams());
        log.setTradeNo(message.getTradeNo());
        log.setNotifyId(message.getNotifyId());
        payNotifyLogRepository.save(log);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertWechatRefundLog(PayNotifyMessage message) {
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(StepType.REFUND);
        log.setPayChannel(PayChannel.WECHAT);
        log.setParams(message.getParams());
        log.setTradeNo(message.getTradeNo());
        log.setRefundNo(message.getRefundNo());
        log.setNotifyId(message.getNotifyId());
        payNotifyLogRepository.save(log);
    }

    @Override
    public PayNotifyLog selectById(Long id) {
        return payNotifyLogRepository.findById(id);
    }

    @Override
    public void playbackSuccess(Long id) {
        payNotifyLogRepository.markPlaybackSuccess(id);
    }
}
