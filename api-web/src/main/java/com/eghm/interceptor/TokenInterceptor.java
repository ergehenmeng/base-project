package com.eghm.interceptor;

import com.eghm.annotation.SkipAccess;
import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.RequestException;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.Token;
import com.eghm.service.common.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆令牌验证,主要用于app,pc等前后端分离
 * 默认针对全部接口进行登陆校验校验成功后将用户信息保存到线程变量中
 * 如果添加@SkipAccess则跳过登陆校验,但如果传递过来有相关用户登陆信息
 * 依旧将会将用户信息存入到线程变量中
 *
 * @author 二哥很猛
 * @date 2018/1/23 12:02
 */
@Slf4j
public class TokenInterceptor implements InterceptorAdapter {

    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!supportHandler(handler)) {
            return true;
        }

        boolean skipAccess = this.skipAccess(handler);
        RequestMessage message = RequestThreadLocal.get();
        this.tryLoginVerify(request.getHeader(AppHeader.ACCESS_TOKEN), request.getHeader(AppHeader.REFRESH_TOKEN), message, !skipAccess);
        return true;
    }


    /**
     * 尝试获取登陆用户的信息(前提用户确实已经登陆,如果没有登陆则获取不到)
     *
     * @param accessToken 登陆的token
     * @param refreshToken 刷新的token
     * @param message 用户信息
     * @param exception true:如果获取用户信息失败则会抛异常,false:获取用户信息失败不做处理
     */
    private void tryLoginVerify(@Nullable String accessToken, @Nullable String refreshToken, RequestMessage message, boolean exception) {
        if (accessToken == null || refreshToken == null) {
            return;
        }
        Token token = tokenService.getByAccessToken(accessToken);
        if (token != null) {
            this.verifyBind(token, message, exception);
            return;
        }
        token = tokenService.getByRefreshToken(refreshToken);
        if (token != null) {
            //在accessToken过期时,可通过refreshToken进行刷新用户信息
            tokenService.cacheToken(token);
            this.verifyBind(token, message, exception);
            return;
        }
        Token offlineToken = tokenService.getOfflineToken(accessToken);
        if (offlineToken != null && exception) {
            log.error("用户其他设备登陆,accessToken:[{}],userId:[{}]", accessToken, offlineToken.getUserId());
            throw new RequestException(ErrorCode.KICK_OFF_LINE);
        }
        if (exception) {
            log.error("令牌为空,accessToken:[{}],refreshToken:[{}]", accessToken, refreshToken);
            throw new RequestException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
        }
    }

    /**
     * 校验并绑定用户信息
     *
     * @param token 用户信息
     * @param message 线程变量信息
     * @param exception 校验失败是否需要抛异常
     */
    private void verifyBind(Token token, RequestMessage message, boolean exception) {
        if (token.getChannel().equals(message.getChannel())) {
            message.setUserId(token.getUserId());
            message.setSecret(token.getSecret());
        }else if (exception) {
            log.error("令牌为空,token:[{}],sourceChannel:[{}],targetChannel:[{}]", token.getToken(), message.getChannel(), token.getChannel());
            throw new RequestException(ErrorCode.ACCESS_TOKEN_TIMEOUT);
        }
    }


    /**
     * 是否需要登陆校验
     *
     * @param handler handlerMethod
     * @return true:需要验签 false不需要
     */
    private boolean skipAccess(Object handler) {
        return this.getAnnotation(handler, SkipAccess.class) != null;
    }

}
