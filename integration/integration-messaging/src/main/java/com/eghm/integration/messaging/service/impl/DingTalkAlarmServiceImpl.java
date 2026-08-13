package com.eghm.integration.messaging.service.impl;

import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.enums.AlarmChannel;
import com.eghm.foundation.core.service.JsonService;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
public class DingTalkAlarmServiceImpl extends AbstractAlarmService {

    private final ApplicationProperties applicationProperties;
    
    public DingTalkAlarmServiceImpl(JsonService jsonService, ApplicationProperties applicationProperties) {
        super(jsonService, applicationProperties);
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected AlarmChannel getChannel() {
        return AlarmChannel.DING_TALK;
    }
    
    @Override
    protected String createRequestUrl() {
        ApplicationProperties.AlarmProperties alarmProperties = applicationProperties.getAlarm();
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("access_token", alarmProperties.getWebHook());
        if (isNotBlank(alarmProperties.getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + alarmProperties.getSecret();
            String sign = SecureUtil.hmacSha256(alarmProperties.getSecret()).digestBase64(unSign, true);
            paramMap.put("timestamp", timestamp);
            paramMap.put("sign", sign);
        }
        return "https://oapi.dingtalk.com/robot/send?" + URLUtil.buildQuery(paramMap, StandardCharsets.UTF_8);
    }
    
    @Override
    protected void logResponse(String responseBody) {
        log.info("发送钉钉消息成功, 返回结果 [{}]", responseBody);
    }
}