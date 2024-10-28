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
     * 根据模板发送短信
     *
     * @param mobile  手机号
     * @param templateId 模板id
     * @param params 模板参数
     * @return 发送状态 0:发送中 1:已发送 2:发送失败
     */
    int sendSms(String mobile, String templateId, Map<String, Object> params);
}
