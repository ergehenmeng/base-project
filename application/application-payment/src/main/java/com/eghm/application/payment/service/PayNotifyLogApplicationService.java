package com.eghm.application.payment.service;

import com.eghm.domain.payment.model.PayNotifyLog;
import com.eghm.domain.payment.enums.StepType;
import com.eghm.application.payment.dto.PayNotifyMessage;

import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/26
 */
public interface PayNotifyLogApplicationService {

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
     * @param message 通知消息
     */
    void insertWechatPayLog(PayNotifyMessage message);

    /**
     * 添加微信退款异步通知
     *
     * @param message 通知消息
     */
    void insertWechatRefundLog(PayNotifyMessage message);

    /**
     * 根据id查询异步回调信息
     *
     * @param id id
     * @return 异步回调
     */
    PayNotifyLog selectById(Long id);

    /**
     * 修改异步回调状态
     *
     * @param id id
     */
    void playbackSuccess(Long id);
}
