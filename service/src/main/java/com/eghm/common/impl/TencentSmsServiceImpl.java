package com.eghm.common.impl;

import com.eghm.common.JsonService;
import com.eghm.common.SendSmsService;
import com.eghm.configuration.SystemProperties;
import com.eghm.enums.SmsType;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @since 2024/10/29
 */

@Slf4j
@AllArgsConstructor
public class TencentSmsServiceImpl implements SendSmsService {

    private final JsonService jsonService;

    private final SystemProperties systemProperties;

    private static final String SUCCESS = "OK";

    @Override
    public int sendSms(String mobile, SmsType smsType, String... params) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(systemProperties.getSms().getSignName());
        request.setTemplateId(smsType.getTemplateId());
        request.setPhoneNumberSet(new String[] { mobile });
        request.setTemplateParamSet(params);
        try {
            SendSmsResponse response = getClient().SendSms(request);
            return SUCCESS.equals(response.getSendStatusSet()[0].getCode()) ? 1 : 0;
        } catch (TencentCloudSDKException e) {
            log.error("腾讯短信发送异常 [{}] [{}] [{}]", mobile, smsType, jsonService.toJson(params), e);
        }
        return 2;
    }

    /**
     * 短信client
     *
     * @return client
     */
    private SmsClient getClient() {
        SystemProperties.Sms sms = systemProperties.getSms();
        Credential credential = new Credential(sms.getKeyId(), sms.getSecretKey());
        return new SmsClient(credential, "ap-shanghai");
    }
}
