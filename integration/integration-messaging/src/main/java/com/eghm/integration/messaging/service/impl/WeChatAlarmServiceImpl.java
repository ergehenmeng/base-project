package com.eghm.integration.messaging.service.impl;

import com.eghm.foundation.core.service.JsonService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.enums.AlarmChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 企业微信报警实现
 *
 * @author 二哥很猛
 * @since 2024/6/14
 */
@Slf4j
public class WeChatAlarmServiceImpl extends AbstractAlarmService {

    private final ApplicationProperties applicationProperties;
    
    public WeChatAlarmServiceImpl(JsonService jsonService, ApplicationProperties applicationProperties) {
        super(jsonService, applicationProperties);
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected AlarmChannel getChannel() {
        return AlarmChannel.ENTERPRISE_WECHAT;
    }

    @Override
    protected String createRequestUrl() {
        return applicationProperties.getAlarm().getWebHook();
    }
    
    /**
     * 打印响应日志
     *
     * @param responseBody 内容
     */
    protected void logResponse(String responseBody) {
        log.info("发送企业微信消息成功, 返回结果 [{}]", responseBody);
    }
}