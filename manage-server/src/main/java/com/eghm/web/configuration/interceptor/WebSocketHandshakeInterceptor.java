package com.eghm.web.configuration.interceptor;

import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.dto.ext.UserToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

import static com.eghm.constants.CommonConstant.SECURITY_USER;

/**
 * websocket拦截器
 * 将token放入websocket session中
 *
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@AllArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final UserTokenService userTokenService;

    private final SystemProperties.ManageProperties manageProperties;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        HttpServletRequest serverRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Optional<UserToken> optional = userTokenService.parseToken(serverRequest.getParameter(manageProperties.getToken().getTokenName()));
        boolean present = optional.isPresent();
        if (present) {
            attributes.put(SECURITY_USER, optional.get());
        }
        return present;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {
        log.info("有新客户端接入 [{}]", request.getRemoteAddress());
    }
}
