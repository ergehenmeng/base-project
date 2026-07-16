package com.eghm.integration.messaging.service.impl;

import com.eghm.integration.messaging.service.SendSmsService;
import com.eghm.foundation.core.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 默认短信发送实现
 *
 * @author 二哥很猛
 * @since 2019/8/20 17:01
 */
@Slf4j
@AllArgsConstructor
public class DefaultSmsServiceImpl implements SendSmsService {

    @Override
    public int sendSms(String mobile, TemplateType templateType, String... params) {
        log.info("单手机号短信发送:[{}] [{}] [{}]", mobile, templateType, params);
        return 1;
    }

    @Override
    public int sendSms(List<String> mobileList, TemplateType templateType, String... params) {
        log.info("多手机号短信发送: [{}] [{}] [{}]", mobileList, templateType, params);
        return 1;
    }

}
