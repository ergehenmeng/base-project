package com.eghm.configuration;

import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.common.impl.DefaultAlarmServiceImpl;
import com.eghm.common.impl.DingTalkAlarmServiceImpl;
import com.eghm.common.impl.FeiShuAlarmServiceImpl;
import com.eghm.common.impl.WeChatAlarmServiceImpl;
import com.eghm.enums.AlarmType;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.eghm.utils.StringUtil.isBlank;

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
