package com.eghm.service.pay.impl;

import com.eghm.mapper.PayNotifyLogMapper;
import com.eghm.model.PayNotifyLog;
import com.eghm.service.pay.PayNotifyLogService;
import com.eghm.service.pay.enums.NotifyType;
import com.eghm.service.pay.enums.TradeType;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    @Async
    public void insertAliLog(Map<String, String> params, NotifyType notifyType) {
        PayNotifyLog log = new PayNotifyLog();
        log.setNotifyType(notifyType.name());
        log.setTradeType(TradeType.ALI_PAY.getCode());
        log.setParams(new Gson().toJson(params));
        log.setOutTradeNo(params.get("out_trade_no"));
        log.setNotifyId(params.get("notify_id"));
        if (notifyType == NotifyType.REFUND) {
            log.setOutTradeNo(params.get("out_biz_no"));
        }
        payNotifyLogMapper.insert(log);
    }

    @Override
    @Async
    public void insertWechatPayLog(WxPayOrderNotifyV3Result result) {
        WxPayOrderNotifyV3Result.DecryptNotifyResult notifyResult = result.getResult();
        PayNotifyLog log = new PayNotifyLog();
        log.setNotifyType(NotifyType.PAY.name());
        log.setTradeType(TradeType.WECHAT.getCode());
        log.setParams(new Gson().toJson(notifyResult));
        log.setOutTradeNo(notifyResult.getOutTradeNo());
        log.setNotifyId(result.getRawData().getId());
        payNotifyLogMapper.insert(log);
    }

    @Override
    @Async
    public void insertWechatRefundLog(WxPayRefundNotifyV3Result result) {
        WxPayRefundNotifyV3Result.DecryptNotifyResult notifyResult = result.getResult();
        PayNotifyLog log = new PayNotifyLog();
        log.setNotifyType(NotifyType.REFUND.name());
        log.setTradeType(TradeType.WECHAT.getCode());
        log.setParams(new Gson().toJson(notifyResult));
        log.setOutTradeNo(notifyResult.getOutTradeNo());
        log.setOutRefundNo(notifyResult.getOutRefundNo());
        log.setNotifyId(result.getRawData().getId());
        payNotifyLogMapper.insert(log);
    }
}
