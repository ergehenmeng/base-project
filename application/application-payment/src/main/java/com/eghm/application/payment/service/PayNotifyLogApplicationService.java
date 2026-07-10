package com.eghm.application.payment.service;

import com.eghm.domain.payment.enums.PayChannel;
import com.eghm.domain.payment.model.PayNotifyLog;
import com.eghm.domain.payment.enums.StepType;
import com.eghm.application.payment.dto.PayNotifyMessage;
import com.eghm.domain.payment.repository.PayNotifyLogRepository;
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

@Service
@AllArgsConstructor
public class PayNotifyLogApplicationService {
    
    private final PayNotifyLogRepository payNotifyLogRepository;
    
    /**
     * 添加支付宝异步通知日志
     *
     * @param params   所有参数
     * @param stepType 通知类型
     */
    @Async
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

    /**
     * 添加微信支付异步通知
     *
     * @param message 通知消息
     */
    @Async
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
    /**
     * 添加微信退款异步通知
     *
     * @param message 通知消息
     */
    @Async
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
    
    /**
     * 根据id查询异步回调信息
     *
     * @param id id
     * @return 异步回调
     */
    public PayNotifyLog selectById(Long id) {
        return payNotifyLogRepository.findById(id);
    }

    /**
     * 修改异步回调状态
     *
     * @param id id
     */
    public void playbackSuccess(Long id) {
        payNotifyLogRepository.markPlaybackSuccess(id);
    }
}
