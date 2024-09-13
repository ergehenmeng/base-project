package com.eghm.web.configuration;

import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.web.configuration.interceptor.WebSocketHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.eghm.constant.CommonConstant.WEBSOCKET_PREFIX;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final SystemProperties systemProperties;

    private final UserTokenService userTokenService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置websocket连接地址后缀
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").addInterceptors(webSocketHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("");
        registry.setApplicationDestinationPrefixes(WEBSOCKET_PREFIX);
    }

    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor() {
        return new WebSocketHandshakeInterceptor(userTokenService, systemProperties.getManage());
    }
}
