package com.eghm.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpMapConfigImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wyb
 * @date 2021/12/4 下午3:19
 */
@Configuration
@EnableConfigurationProperties(WeChatProperties.class)
public class WeChatConfiguration {

    private WeChatProperties weChatProperties;

    public WeChatConfiguration(WeChatProperties weChatProperties) {
        this.weChatProperties = weChatProperties;
    }

    /**
     * 微信公众号
     */
    @Bean
    @ConditionalOnProperty(prefix = "wechat", name = "mp-app-id")
    public WxMpService wxMpService() {
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
    @ConditionalOnProperty(prefix = "wechat", name = "mini-app-id")
    public WxMaService wxMaService() {
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(weChatProperties.getMpAppId());
        config.setSecret(weChatProperties.getMpAppSecret());
        service.setWxMaConfig(config);
        return service;
    }

    /**
     * 微信支付
     */
    @Bean
    @ConditionalOnProperty(prefix = "wechat", name = "pay-app-id")
    public WxPayService wxPayService() {
        WxPayService service = new WxPayServiceImpl();
        WxPayConfig config = new WxPayConfig();
        config.setAppId(weChatProperties.getPayAppId());
        config.setNotifyUrl(weChatProperties.getPayNotifyUrl());
        config.setMchId(weChatProperties.getPayMerchantId());
        config.setMchKey(weChatProperties.getPayMerchantKey());
        service.setConfig(config);
        return service;
    }

}
