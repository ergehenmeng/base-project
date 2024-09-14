package com.eghm.configuration;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @since 2022/7/25
 */
@Configuration
@AllArgsConstructor
public class AliPayConfig {

    private final SystemProperties systemProperties;

    @Bean
    @ConditionalOnProperty(prefix = "system.ali-pay", name = "app-id")
    public Config config() {
        SystemProperties.AliPayProperties properties = systemProperties.getAliPay();
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = properties.getAppId();
        config.merchantPrivateKey = properties.getPrivateKey();
        config.alipayPublicKey = properties.getPublicKey();
        config.encryptKey = properties.getEncryptKey();
        Factory.setOptions(config);
        return config;
    }
}
