package com.eghm.infrastructure.integration.common.impl;

import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.infrastructure.shared.configuration.properties.ApplicationProperties;
import com.eghm.enums.AlarmType;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

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
    protected AlarmType getAlarmType() {
        return AlarmType.DING_TALK;
    }
    
    @Override
    protected String createRequestUrl() {
        ApplicationProperties.Alarm alarm = applicationProperties.getAlarm();
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("access_token", alarm.getWebHook());
        if (isNotBlank(alarm.getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + alarm.getSecret();
            String sign = SecureUtil.hmacSha256(alarm.getSecret()).digestBase64(unSign, true);
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