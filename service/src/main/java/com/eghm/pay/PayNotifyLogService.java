package com.eghm.pay;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.pay.enums.StepType;
import com.eghm.vo.business.log.PayNotifyLogResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;

import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/26
 */
public interface PayNotifyLogService {

    /**
     * 分页查询支付退款异步请求的日志
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<PayNotifyLogResponse> getByPage(PayLogQueryRequest request);

    /**
     * 添加支付宝异步通知日志
     *
     * @param params   所有参数
     * @param stepType 通知类型
     */
    void insertAliLog(Map<String, String> params, StepType stepType);

    /**
     * 添加微信支付异步通知
     *
     * @param result 通知原始数据
     */
    void insertWechatPayLog(WxPayNotifyV3Result result);

    /**
     * 添加微信退款异步通知
     *
     * @param result 通知原始数据
     */
    void insertWechatRefundLog(WxPayRefundNotifyV3Result result);
}
