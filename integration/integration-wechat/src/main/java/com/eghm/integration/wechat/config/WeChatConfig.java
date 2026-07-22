package com.eghm.integration.wechat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.eghm.foundation.core.configuration.ApplicationProperties;
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

    /**
     * 微信公众号
     */
    @Bean
    @ConditionalOnProperty(prefix = "system.wechat.mp", name = "app-id")
    public WxMpService wxMpService(ApplicationProperties applicationProperties) {
        ApplicationProperties.WeChatProperties weChatProperties = applicationProperties.getWechat();
        WxMpService service = new WxMpServiceImpl();
        WxMpMapConfigImpl config = new WxMpMapConfigImpl();
        config.setAppId(weChatProperties.getMp().getAppId());
        config.setSecret(weChatProperties.getMp().getAppSecret());
        service.setWxMpConfigStorage(config);
        return service;
    }

    /**
     * 微信小程序
     */
    @Bean
    @ConditionalOnProperty(prefix = "system.wechat.ma", name = "app-id")
    public WxMaService wxMaService(ApplicationProperties applicationProperties) {
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        ApplicationProperties.WeChatProperties weChatProperties = applicationProperties.getWechat();
        config.setAppid(weChatProperties.getMa().getAppId());
        config.setSecret(weChatProperties.getMa().getAppSecret());
        service.setWxMaConfig(config);
        return service;
    }

}
