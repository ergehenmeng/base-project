package com.eghm.configuration;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.eghm.common.JsonService;
import com.eghm.common.SendSmsService;
import com.eghm.common.impl.AliSmsServiceImpl;
import com.eghm.common.impl.TencentSmsServiceImpl;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @since 2024/10/29
 */

@Configuration
@AllArgsConstructor
public class SmsConfig {

    private final SystemProperties systemProperties;

    @Bean
    @ConditionalOnProperty(value = "system.sms.sms-type", havingValue = "ali")
    public Client client() throws Exception {
        Config config = new Config()
                .setAccessKeyId(systemProperties.getSms().getKeyId())
                .setAccessKeySecret(systemProperties.getSms().getSecretKey());
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Bean
    @ConditionalOnProperty(value = "system.sms.sms-type", havingValue = "ali")
    public SendSmsService aliSmsService(Client client, JsonService jsonService, SystemProperties systemProperties) {
        return new AliSmsServiceImpl(client, jsonService, systemProperties);
    }

    @Bean
    @ConditionalOnProperty(value = "system.sms.sms-type", havingValue = "tencent")
    public SmsClient smsClient() {
        SystemProperties.Sms sms = systemProperties.getSms();
        Credential credential = new Credential(sms.getKeyId(), sms.getSecretKey());
        return new SmsClient(credential, "ap-shanghai");
    }

    @Bean
    @ConditionalOnProperty(value = "system.sms.sms-type", havingValue = "tencent")
    public SendSmsService tencentSmsService(SmsClient smsClient, JsonService jsonService, SystemProperties systemProperties) {
        return new TencentSmsServiceImpl(smsClient, jsonService, systemProperties);
    }

}
