package com.eghm.configuration;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.eghm.common.JsonService;
import com.eghm.common.SendSmsService;
import com.eghm.common.impl.AliSmsServiceImpl;
import com.eghm.common.impl.DefaultSmsServiceImpl;
import com.eghm.common.impl.TencentSmsServiceImpl;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
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
    @ConditionalOnProperty(prefix = "system.sms", name = "channel", matchIfMissing = true)
    public SendSmsService defaultSmsService() {
        return new DefaultSmsServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.sms", name = "channel", havingValue = "ali")
    public SendSmsService aliSmsService(JsonService jsonService, ApplicationProperties applicationProperties) throws Exception {
        Config config = new Config()
                .setAccessKeyId(applicationProperties.getSms().getKeyId())
                .setAccessKeySecret(applicationProperties.getSms().getSecretKey());
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new AliSmsServiceImpl(new Client(config), jsonService, applicationProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.sms", name = "channel", havingValue = "tencent")
    public SendSmsService tencentSmsService(JsonService jsonService, ApplicationProperties applicationProperties) {
        ApplicationProperties.Sms sms = applicationProperties.getSms();
        Credential credential = new Credential(sms.getKeyId(), sms.getSecretKey());
        return new TencentSmsServiceImpl(new SmsClient(credential, "ap-shanghai"), jsonService, applicationProperties);
    }

}
