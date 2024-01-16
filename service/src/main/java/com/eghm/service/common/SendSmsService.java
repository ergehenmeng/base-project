package com.eghm.service.common;

/**
 * 发送短信接口
 *
 * @author 二哥很猛
 * @date 2019/8/20 17:00
 */
public interface SendSmsService {

    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return 发送状态 0:发送中 1:已发送 2:发送失败
     */
    int sendSms(String mobile, String content);
}
