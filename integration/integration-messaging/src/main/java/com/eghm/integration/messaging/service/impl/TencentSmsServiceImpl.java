package com.eghm.integration.messaging.service.impl;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.enums.TemplateType;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.integration.messaging.service.SendSmsService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/10/29
 */

@Slf4j
@AllArgsConstructor
public class TencentSmsServiceImpl implements SendSmsService {
    
    private final SmsClient smsClient;

    private final JsonService jsonService;

    private final ApplicationProperties applicationProperties;

    private static final String SUCCESS = "OK";

    @Override
    public int sendSms(String mobile, TemplateType templateType, String... params) {
        return this.sendSms(List.of(mobile), templateType, params);
    }

    @Override
    public int sendSms(List<String> mobileList, TemplateType templateType, String... params) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(applicationProperties.getSms().getSignName());
        request.setTemplateId(templateType.getTemplateId());
        request.setPhoneNumberSet(mobileList.toArray(new String[]{}));
        request.setTemplateParamSet(params);
        try {
            SendSmsResponse response = smsClient.SendSms(request);
            return SUCCESS.equals(response.getSendStatusSet()[0].getCode()) ? 1 : 0;
        } catch (TencentCloudSDKException e) {
            log.error("腾讯短信发送异常 [{}] [{}] [{}]", mobileList, templateType, jsonService.toJson(params), e);
        }
        return 2;
    }

}
