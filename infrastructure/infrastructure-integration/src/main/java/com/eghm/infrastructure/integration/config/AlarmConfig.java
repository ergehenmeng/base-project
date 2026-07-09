package com.eghm.infrastructure.integration.config;

import com.eghm.domain.shared.service.AlarmService;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.infrastructure.integration.common.impl.DefaultAlarmServiceImpl;
import com.eghm.infrastructure.integration.common.impl.DingTalkAlarmServiceImpl;
import com.eghm.infrastructure.integration.common.impl.FeiShuAlarmServiceImpl;
import com.eghm.infrastructure.integration.common.impl.WeChatAlarmServiceImpl;
import com.eghm.enums.AlarmType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * 告警适配器配置.
 *
 * @author 二哥很猛
 */
@Configuration
public class AlarmConfig {

    @Bean
    public AlarmService alarmService(JsonService jsonService, ApplicationProperties applicationProperties) {
        ApplicationProperties.Alarm alarm = applicationProperties.getAlarm();
        if (alarm.getType() == AlarmType.DEFAULT) {
            return new DefaultAlarmServiceImpl();
        }
        if (isBlank(alarm.getWebHook())) {
            throw new BusinessException(ErrorCode.WEB_HOOK_NULL);
        }
        if (alarm.getType() == AlarmType.DING_TALK) {
            return new DingTalkAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarm.getType() == AlarmType.FEI_SHU) {
            return new FeiShuAlarmServiceImpl(jsonService, applicationProperties);
        }
        if (alarm.getType() == AlarmType.ENTERPRISE_WECHAT) {
            return new WeChatAlarmServiceImpl(jsonService, applicationProperties);
        }
        return new DefaultAlarmServiceImpl();
    }
}
