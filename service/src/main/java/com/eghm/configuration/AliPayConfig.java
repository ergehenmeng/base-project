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

    @Bean
    @ConditionalOnProperty(prefix = "system.ali-pay", name = "app-id")
    public Config config(SystemProperties systemProperties) {
        SystemProperties.AliPay pay = systemProperties.getAli().getPay();
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = pay.getAppId();
        config.merchantPrivateKey = pay.getPrivateKey();
        config.alipayPublicKey = pay.getPublicKey();
        config.encryptKey = pay.getEncryptKey();
        Factory.setOptions(config);
        return config;
    }
}
