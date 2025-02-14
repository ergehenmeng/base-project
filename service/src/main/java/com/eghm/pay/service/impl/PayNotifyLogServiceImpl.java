package com.eghm.pay.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.mapper.PayNotifyLogMapper;
import com.eghm.model.PayNotifyLog;
import com.eghm.pay.service.PayNotifyLogService;
import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import com.eghm.vo.business.log.PayNotifyLogResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
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
@Service("payNotifyLogService")
@AllArgsConstructor
public class PayNotifyLogServiceImpl implements PayNotifyLogService {

    private final PayNotifyLogMapper payNotifyLogMapper;

    @Override
    public Page<PayNotifyLogResponse> getByPage(PayLogQueryRequest request) {
        return payNotifyLogMapper.getByPage(request.createPage(), request);
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
        payNotifyLogMapper.insert(log);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertWechatPayLog(WxPayNotifyV3Result result) {
        WxPayNotifyV3Result.DecryptNotifyResult notifyResult = result.getResult();
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(StepType.PAY);
        log.setPayChannel(PayChannel.WECHAT);
        log.setParams(new Gson().toJson(notifyResult));
        log.setTradeNo(notifyResult.getOutTradeNo());
        log.setNotifyId(result.getRawData().getId());
        payNotifyLogMapper.insert(log);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertWechatRefundLog(WxPayRefundNotifyV3Result result) {
        WxPayRefundNotifyV3Result.DecryptNotifyResult notifyResult = result.getResult();
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(StepType.REFUND);
        log.setPayChannel(PayChannel.WECHAT);
        log.setParams(new Gson().toJson(notifyResult));
        log.setTradeNo(notifyResult.getOutTradeNo());
        log.setRefundNo(notifyResult.getOutRefundNo());
        log.setNotifyId(result.getRawData().getId());
        payNotifyLogMapper.insert(log);
    }
}
