package com.eghm.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wyb
 * @date 2021/12/4 下午3:21
 */
@ConfigurationProperties(prefix = WeChatProperties.PREFIX)
@Component
@Data
public class WeChatProperties {

    static final String PREFIX = "wechat";

    /**
     * 公众号appId
     */
    private String mpAppId;

    /**
     * 公众号appSecret
     */
    private String mpAppSecret;

    /**
     * 小程序appId
     */
    private String miniAppId;

    /**
     * 小程序appId
     */
    private String miniAppSecret;

    /**
     * 微信支付所在公众号的appId
     */
    private String payAppId;

    /**
     * 微信支付商户号
     */
    private String payMerchantId;

    /**
     * 微信支付商户密钥
     */
    private String payMerchantKey;

    /**
     * apiV3 秘钥
     */
    private String payApiV3Key;

    /**
     * 私钥路径 支持classpath
     */
    private String payPrivateKeyPath;

    /**
     * apiV3证书序列号
     */
    private String paySerialNo;

    /**
     * 微信支付异步回调地址 相对路径不会变
     */
    private String payNotifyUrl;

}
