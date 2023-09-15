package com.eghm.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.mapper.PayNotifyLogMapper;
import com.eghm.model.PayNotifyLog;
import com.eghm.service.pay.PayNotifyLogService;
import com.eghm.service.pay.enums.PayChannel;
import com.eghm.service.pay.enums.StepType;
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
 * @date 2022/7/26
 */
@Service("payNotifyLogService")
@AllArgsConstructor
public class PayNotifyLogServiceImpl implements PayNotifyLogService {

    private final PayNotifyLogMapper payNotifyLogMapper;

    @Override
    public Page<PayNotifyLog> getByPage(PayLogQueryRequest request) {
        LambdaQueryWrapper<PayNotifyLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getPayChannel() != null, PayNotifyLog::getPayChannel, request.getPayChannel());
        wrapper.eq(request.getStepType() != null, PayNotifyLog::getStepType, request.getPayChannel());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper -> queryWrapper
                .like(PayNotifyLog::getOutTradeNo, request.getQueryName())
                .or()
                .like(PayNotifyLog::getOutRefundNo, request.getQueryName()));
        return payNotifyLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertAliLog(Map<String, String> params, StepType stepType) {
        PayNotifyLog log = new PayNotifyLog();
        log.setStepType(stepType);
        log.setPayChannel(PayChannel.ALIPAY);
        log.setParams(new Gson().toJson(params));
        log.setOutTradeNo(params.get("out_trade_no"));
        log.setNotifyId(params.get("notify_id"));
        if (stepType == StepType.REFUND) {
            log.setOutRefundNo(params.get("out_biz_no"));
        }
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
        log.setOutTradeNo(notifyResult.getOutTradeNo());
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
        log.setOutTradeNo(notifyResult.getOutTradeNo());
        log.setOutRefundNo(notifyResult.getOutRefundNo());
        log.setNotifyId(result.getRawData().getId());
        payNotifyLogMapper.insert(log);
    }
}
