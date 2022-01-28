package com.eghm.web.configuration.interceptor;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.JwtToken;
import com.eghm.model.dto.ext.RequestMessage;
import com.eghm.service.common.JwtTokenService;
import com.eghm.web.annotation.SkipAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * jwtToken拦截器 默认不注册
 * 如果注册,需要将{@link TokenInterceptor}屏蔽,同时需要修改用户登陆时token生成规则
 * 后期可能会将redisToken和jwtToken进行抽取,进行自动切换
 * @author 殿小二
 * @date 2020/8/28
 */
@Slf4j
@Deprecated
public class JwtTokenInterceptor implements InterceptorAdapter {

    private JwtTokenService jwtTokenService;

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!supportHandler(handler)) {
            return true;
        }
        boolean skipAccess = this.getAnnotation(handler, SkipAccess.class) != null;
        RequestMessage message = ApiHolder.get();
        this.tryLoginVerify(request.getHeader(AppHeader.TOKEN), message, !skipAccess);
        return true;

    }

    private void tryLoginVerify(String token, RequestMessage message, boolean exception) {
        if (token == null && !exception) {
            return;
        }
        if (token == null) {
            throw new ParameterException(ErrorCode.ACCESS_TOKEN_NULL);
        }
        Optional<JwtToken> jwtToken = jwtTokenService.parseToken(token);
        JwtToken jwt = jwtToken.orElseThrow(() -> new ParameterException(ErrorCode.ACCESS_TOKEN_TIMEOUT));

        message.setUserId(jwt.getId());
        // 无法从token总解析出userId,但该接口确实需要登陆
        if (exception && message.getUserId() == null) {
            log.warn("令牌解析为空,token:[{}]", token);
            throw new ParameterException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
        }
    }

}
