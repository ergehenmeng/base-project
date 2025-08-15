package com.eghm.configuration;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝支付配置
 *
 * @author 二哥很猛
 * @since 2022/7/25
 */
@Configuration
@AllArgsConstructor
public class AliPayConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system.ali.pay", name = "app-id")
    public DefaultAlipayClient alipayClient(SystemProperties systemProperties) throws AlipayApiException {
        SystemProperties.AliPay pay = systemProperties.getAli().getPay();
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(pay.getAppId());
        alipayConfig.setPrivateKey(pay.getPrivateKey());
        alipayConfig.setAlipayPublicKey(pay.getPublicKey());
        return new DefaultAlipayClient(alipayConfig);
    }
}
