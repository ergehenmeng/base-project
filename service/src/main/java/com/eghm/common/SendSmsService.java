package com.eghm.common;

import java.util.Map;

/**
 * 发送短信接口
 *
 * @author 二哥很猛
 * @since 2019/8/20 17:00
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

    /**
     * 根据模板发送短信
     *
     * @param mobile  手机号
     * @param templateName 模板名称
     * @param params 模板参数
     * @return 发送状态 0:发送中 1:已发送 2:发送失败
     */
    int sendSms(String mobile, String templateName, Map<String, Object> params);
}
