package com.eghm.configuration;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @date 2022/7/25
 */
@Configuration
@EnableConfigurationProperties(SystemProperties.class)
@AllArgsConstructor
public class AliPayConfig {

    private final SystemProperties systemProperties;

    @Bean
    public Config config() {
        SystemProperties.AliPayProperties properties = systemProperties.getAliPay();
        Config config = new Config();
        config.protocol = properties.getProtocol();
        config.gatewayHost = properties.getHost();
        config.signType = properties.getSignType();
        config.appId = properties.getAppId();
        config.merchantPrivateKey = properties.getPrivateKey();
        config.alipayPublicKey = properties.getPublicKey();
        config.encryptKey = properties.getEncryptKey();
        Factory.setOptions(config);
        return config;
    }
}
