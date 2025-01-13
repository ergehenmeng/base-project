package com.eghm.configuration;

import com.eghm.common.JsonService;
import com.eghm.common.SendSmsService;
import com.eghm.common.impl.AliSmsServiceImpl;
import com.eghm.common.impl.TencentSmsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置 阿里云短信 + 腾讯云短信
 *
 * @author 二哥很猛
 * @since 2024/10/29
 */

@Configuration
@AllArgsConstructor
public class SmsConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system.sms", name = "channel", havingValue = "ali", matchIfMissing = true)
    public SendSmsService aliSmsService(JsonService jsonService, SystemProperties systemProperties) {
        return new AliSmsServiceImpl(jsonService, systemProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.sms", name = "channel", havingValue = "tencent")
    public SendSmsService tencentSmsService(JsonService jsonService, SystemProperties systemProperties) {
        return new TencentSmsServiceImpl(jsonService, systemProperties);
    }

}
