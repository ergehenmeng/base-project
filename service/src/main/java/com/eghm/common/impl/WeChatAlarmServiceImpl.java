package com.eghm.common.impl;

import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.enums.AlarmType;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * 企业微信报警实现
 *
 * @author 二哥很猛
 * @since 2024/6/14
 */
@Slf4j
public class WeChatAlarmServiceImpl extends AbstractAlarmService {

    private final SystemProperties systemProperties;
    
    public WeChatAlarmServiceImpl(JsonService jsonService, SystemProperties systemProperties,
                                  RateLimiterRegistry rateLimiterRegistry) {
        super(jsonService, systemProperties, rateLimiterRegistry);
        this.systemProperties = systemProperties;
    }

    @Override
    protected AlarmType getAlarmType() {
        return AlarmType.ENTERPRISE_WECHAT;
    }

    @Override
    protected String createRequestUrl() {
        return systemProperties.getAlarm().getWebHook();
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