package com.eghm.interfaces.manage.configuration.interceptor;

import com.eghm.application.shared.common.UserTokenService;
import com.eghm.application.shared.dto.ext.UserToken;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

import static com.eghm.constants.ApplicationHeader.TOKEN;
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

    @Override
    public boolean beforeHandshake(@Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response, @Nonnull WebSocketHandler wsHandler, @Nonnull Map<String, Object> attributes) {
        HttpServletRequest serverRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Optional<UserToken> optional = userTokenService.parseToken(serverRequest.getParameter(TOKEN));
        boolean present = optional.isPresent();
        if (present) {
            attributes.put(SECURITY_USER, optional.get());
        }
        return present;
    }

    @Override
    public void afterHandshake(@Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response, @Nonnull WebSocketHandler wsHandler, Exception exception) {
        log.info("有新客户端接入 [{}]", request.getRemoteAddress());
    }
}
