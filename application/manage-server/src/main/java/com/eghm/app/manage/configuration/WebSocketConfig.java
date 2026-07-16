package com.eghm.app.manage.configuration;

import com.eghm.platform.iam.service.UserTokenService;
import com.eghm.app.manage.configuration.interceptor.WebSocketHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.eghm.foundation.core.constants.CommonConstant.WEBSOCKET_PREFIX;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Configuration
@AllArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserTokenService userTokenService;

    private final ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置websocket连接地址后缀
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").addInterceptors(webSocketHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("").setTaskScheduler(taskScheduler).setHeartbeatValue(new long[]{10000, 20000});
        registry.setApplicationDestinationPrefixes(WEBSOCKET_PREFIX);
    }

    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor() {
        return new WebSocketHandshakeInterceptor(userTokenService);
    }
}
