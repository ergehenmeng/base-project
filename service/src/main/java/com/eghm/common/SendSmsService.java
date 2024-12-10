package com.eghm.common;

import com.eghm.enums.TemplateType;

import java.util.List;

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
     * @param templateType 短信模板
     * @param params 模板参数
     * @return 发送状态 0:发送中 1:已发送 2:发送失败
     */
    int sendSms(String mobile, TemplateType templateType, String... params);

    /**
     * 根据模板发送短信
     *
     * @param mobileList  手机号
     * @param templateType 短信模板
     * @param params 参数
     * @return 发送状态 0:发送中 1:已发送 2:发送失败
     */
    int sendSms(List<String> mobileList, TemplateType templateType, String... params);
}
