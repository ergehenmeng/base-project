package com.eghm.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpMapConfigImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @since 2021/12/4 下午3:19
 */
@Configuration
@AllArgsConstructor
public class WeChatConfig {

    private final SystemProperties systemProperties;

    /**
     * 微信公众号
     */
    @Bean
    @ConditionalOnProperty(prefix = "system.wechat", name = "mp-app-id")
    public WxMpService wxMpService() {
        SystemProperties.WeChatProperties weChatProperties = systemProperties.getWechat();
        WxMpService service = new WxMpServiceImpl();
        WxMpMapConfigImpl config = new WxMpMapConfigImpl();
        config.setAppId(weChatProperties.getMpAppId());
        config.setSecret(weChatProperties.getMpAppSecret());
        service.setWxMpConfigStorage(config);
        return service;
    }

    /**
     * 微信小程序
     */
    @Bean
    @ConditionalOnProperty(prefix = "system.wechat", name = "mini-app-id")
    public WxMaService wxMaService() {
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        SystemProperties.WeChatProperties weChatProperties = systemProperties.getWechat();
        config.setAppid(weChatProperties.getMiniAppId());
        config.setSecret(weChatProperties.getMiniAppSecret());
        service.setWxMaConfig(config);
        return service;
    }

    /**
     * 微信支付
     */
    @Bean
    @ConditionalOnProperty(prefix = "system.wechat", name = "pay-app-id")
    public WxPayService wxPayService() {
        WxPayService service = new WxPayServiceImpl();
        WxPayConfig config = new WxPayConfig();
        SystemProperties.WeChatProperties weChatProperties = systemProperties.getWechat();
        config.setAppId(weChatProperties.getPayAppId());
        config.setMchId(weChatProperties.getPayMerchantId());
        config.setSignType(WxPayConstants.SignType.HMAC_SHA256);
        config.setApiV3Key(weChatProperties.getPayApiV3Key());
        config.setCertSerialNo(weChatProperties.getPaySerialNo());
        config.setPrivateKeyPath(weChatProperties.getPayPrivateKeyPath());
        config.setPrivateCertPath(weChatProperties.getPayPrivateCertPath());
        service.setConfig(config);
        return service;
    }

}
