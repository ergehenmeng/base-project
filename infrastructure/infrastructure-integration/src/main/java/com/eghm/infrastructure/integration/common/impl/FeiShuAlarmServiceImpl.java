package com.eghm.infrastructure.integration.common.impl;

import cn.hutool.crypto.SecureUtil;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.dto.ext.FeiShuMsg;
import com.eghm.enums.AlarmType;
import lombok.extern.slf4j.Slf4j;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
public class FeiShuAlarmServiceImpl extends AbstractAlarmService {

    private final ApplicationProperties applicationProperties;
    
    public FeiShuAlarmServiceImpl(JsonService jsonService, ApplicationProperties applicationProperties) {
        super(jsonService, applicationProperties);
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected AlarmType getAlarmType() {
        return AlarmType.FEI_SHU;
    }
    
    @Override
    protected String createRequestUrl() {
        return applicationProperties.getAlarm().getWebHook();
    }
    
    @Override
    public String createTextMsg(String content) {
        FeiShuMsg msg = new FeiShuMsg();
        String builder = super.createMessageContent(content);
        msg.setContent(new FeiShuMsg.Content(builder));
        msg.setMsgType("text");
        if (isNotBlank(applicationProperties.getAlarm().getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + applicationProperties.getAlarm().getSecret();
            String sign = SecureUtil.hmacSha256(applicationProperties.getAlarm().getSecret()).digestBase64(unSign, true);
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