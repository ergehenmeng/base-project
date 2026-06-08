package com.eghm.common.impl;

import cn.hutool.crypto.SecureUtil;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.FeiShuMsg;
import com.eghm.enums.AlarmType;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.extern.slf4j.Slf4j;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
public class FeiShuAlarmServiceImpl extends AbstractAlarmService {

    private final SystemProperties systemProperties;
    
    public FeiShuAlarmServiceImpl(JsonService jsonService, SystemProperties systemProperties,
                                  RateLimiterRegistry rateLimiterRegistry) {
        super(jsonService, systemProperties, rateLimiterRegistry);
        this.systemProperties = systemProperties;
    }

    @Override
    protected AlarmType getAlarmType() {
        return AlarmType.FEI_SHU;
    }
    
    @Override
    protected String createRequestUrl() {
        return systemProperties.getAlarm().getWebHook();
    }
    
    @Override
    public String createTextMsg(String content) {
        FeiShuMsg msg = new FeiShuMsg();
        String builder = super.createMessageContent(content);
        msg.setText(new FeiShuMsg.Text(builder));
        msg.setMsgType("text");
        if (isNotBlank(systemProperties.getAlarm().getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + systemProperties.getAlarm().getSecret();
            String sign = SecureUtil.hmacSha256(systemProperties.getAlarm().getSecret()).digestBase64(unSign, true);
            msg.setTimestamp(timestamp);
            msg.setSign(sign);
        }
        return jsonService.toJson(msg);
    }
    
    @Override
    protected void logResponse(String responseBody) {
        log.info("发送飞书消息成功, 返回结果 [{}]", responseBody);
    }
}