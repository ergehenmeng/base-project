package com.eghm.web.configuration.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.eghm.configuration.SystemProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.List;
import java.util.Map;

/**
 * websocket拦截器
 * 将token放入websocket session中
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@AllArgsConstructor
public class WebSocketHandshakeInterceptor implements ChannelInterceptor {

    private final SystemProperties.ManageProperties manageProperties;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            String tokenName = manageProperties.getToken().getTokenName();
            List<String> token = accessor.getNativeHeader(tokenName);
            Map<String, Object> attributes = accessor.getSessionAttributes();
            if (CollUtil.isNotEmpty(token) && attributes != null) {
                attributes.put(tokenName, token.get(0));
            }
        }
        return message;
    }
}
