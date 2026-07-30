package com.eghm.integration.payment.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
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
public class PayConfig {
    
    @Bean
    @ConditionalOnProperty(prefix = "application.pay.ali", name = "app-id")
    public DefaultAlipayClient alipayClient(ApplicationProperties applicationProperties) throws AlipayApiException {
        ApplicationProperties.PayProperties.AliPay pay = applicationProperties.getPay().getAli();
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(pay.getAppId());
        alipayConfig.setPrivateKey(pay.getPrivateKey());
        alipayConfig.setAlipayPublicKey(pay.getPublicKey());
        return new DefaultAlipayClient(alipayConfig);
    }
    
    @Bean
    @ConditionalOnProperty(prefix = "application.pay.wx", name = "api-v3-key")
    public WxPayService wxPayService(ApplicationProperties applicationProperties) {
        WxPayService service = new WxPayServiceImpl();
        WxPayConfig config = new WxPayConfig();
        ApplicationProperties.PayProperties.WxPay pay = applicationProperties.getPay().getWx();
        config.setMchId(pay.getMchId());
        config.setSignType(WxPayConstants.SignType.HMAC_SHA256);
        config.setApiV3Key(pay.getApiV3Key());
        config.setCertSerialNo(pay.getSerialNo());
        config.setPrivateKeyPath(pay.getPrivateKeyPath());
        service.setConfig(config);
        return service;
    }
}
