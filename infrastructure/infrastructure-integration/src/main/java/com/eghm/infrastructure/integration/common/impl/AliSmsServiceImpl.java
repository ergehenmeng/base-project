package com.eghm.infrastructure.integration.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.application.shared.common.SendSmsService;
import com.eghm.infrastructure.shared.configuration.properties.ApplicationProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.shared.enums.TemplateType;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认短信发送实现
 *
 * @author 二哥很猛
 * @since 2019/8/20 17:01
 */
@Slf4j
@AllArgsConstructor
public class AliSmsServiceImpl implements SendSmsService {
    
    private final Client client;
    
    private final JsonService jsonService;

    private final ApplicationProperties applicationProperties;

    private static final String SUCCESS = "OK";

    @Override
    public int sendSms(String mobile, TemplateType templateType, String... params) {
        return this.sendSms(Lists.newArrayList(mobile), templateType, params);
    }

    @Override
    public int sendSms(List<String> mobileList, TemplateType templateType, String... params) {
        Map<String, Object> param = new HashMap<>(4);
        if (params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                param.put("param" + i, params[i]);
            }
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(applicationProperties.getSms().getSignName());
        request.setPhoneNumbers(CollUtil.join(mobileList, CommonConstant.COMMA));
        request.setTemplateCode(templateType.getTemplateId());
        String jsonParam = jsonService.toJson(param);
        request.setTemplateParam(jsonParam);
        try {
            SendSmsResponse response = client.sendSms(request);
            return SUCCESS.equals(response.getBody().getCode()) ? 1 : 0;
        } catch (Exception e) {
            log.error("阿里云短信发送异常 [{}] [{}] [{}]", mobileList, templateType, jsonParam,  e);
        }
        return 2;
    }

}
