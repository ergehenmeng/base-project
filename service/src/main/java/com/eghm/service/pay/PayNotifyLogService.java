package com.eghm.service.pay;

import com.eghm.service.pay.enums.NotifyType;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;

import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/7/26
 */
public interface PayNotifyLogService {

    /**
     * 添加支付宝异步通知日志
     * @param params 所有参数
     * @param notifyType 通知类型
     */
    void insertAliLog(Map<String, String> params, NotifyType notifyType);

    /**
     * 添加微信支付异步通知
     * @param result 通知原始数据
     */
    void insertWechatPayLog(WxPayOrderNotifyV3Result result);

    /**
     * 添加微信退款异步通知
     * @param result 通知原始数据
     */
    void insertWechatRefundLog(WxPayRefundNotifyV3Result result);
}
