package com.eghm.integration.messaging.config;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.enums.AlarmChannel;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.integration.messaging.service.impl.DefaultAlarmServiceImpl;
import com.eghm.integration.messaging.service.impl.DingTalkAlarmServiceImpl;
import com.eghm.integration.messaging.service.impl.FeiShuAlarmServiceImpl;
import com.eghm.integration.messaging.service.impl.WeChatAlarmServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.eghm.foundation.core.utils.StringUtil.isBlank;

@Configuration
@RequiredArgsConstructor
public class AlarmConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public AlarmService alarmService(JsonService jsonService) {
        ApplicationProperties.AlarmProperties alarmProperties = applicationProperties.getAlarm();
        if (alarmProperties.getChannel() == AlarmChannel.DEFAULT) {
            return new DefaultAlarmServiceImpl();
        }
        if (isBlank(alarmProperties.getWebHook())) {
            throw new BusinessException(ErrorCode.WEB_HOOK_NULL);
        }
        if (alarmProperties.getChannel() == AlarmChannel.DING_TALK) {
            return new DingTalkAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarmProperties.getChannel() == AlarmChannel.FEI_SHU) {
            return new FeiShuAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarmProperties.getChannel() == AlarmChannel.ENTERPRISE_WECHAT) {
            return new WeChatAlarmServiceImpl(jsonService, applicationProperties);
        }
        return new DefaultAlarmServiceImpl();
    }
}
