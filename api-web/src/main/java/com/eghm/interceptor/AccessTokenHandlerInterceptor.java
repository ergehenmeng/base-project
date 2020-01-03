package com.eghm.interceptor;

import com.eghm.annotation.SkipAccess;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.RequestException;
import com.eghm.model.ext.AccessToken;
import com.eghm.model.ext.RequestMessage;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.service.common.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
public class AccessTokenHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestMessage message = RequestThreadLocal.get();
        boolean skipAccess = this.skipAccess(handler);
        this.accessTokenVerify(message.getAccessToken(), message, skipAccess);
        return true;
    }


    /**
     * 登陆校验
     *
     * @param accessToken 令牌
     * @param message     存放用户信息的对象
     * @param skipAccess  是否跳过登陆认证
     */
    private void accessTokenVerify(String accessToken, RequestMessage message, boolean skipAccess) {
        if (accessToken != null) {
            AccessToken token = accessTokenService.getByAccessToken(accessToken);
            boolean accessFail = token != null && token.getChannel().equals(message.getChannel());
            if (accessFail) {
                //用户确实已经登录 跳过不跳过无所谓了
                accessTokenService.saveByAccessToken(token);
                message.setUserId(token.getUserId());
                message.setSignKey(token.getSignKey());
                return;
            }
        }
        //用户未登录或者登录验证失败 但接口需要登录,需抛异常
        if (!skipAccess) {
            log.error("令牌为空,accessToken:[{}]", accessToken);
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
        SkipAccess clientTypeToken = this.getSkipAccessAnnotation(handler);
        return clientTypeToken != null;
    }


    /**
     * 获取handlerMethod上指定类型的注解,如果类上有也会返回
     *
     * @param handler handlerMethod
     * @return AccessToken
     */
    private SkipAccess getSkipAccessAnnotation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            SkipAccess t = method.getMethodAnnotation(SkipAccess.class);
            return t != null ? t : method.getBeanType().getAnnotation(SkipAccess.class);
        }
        return null;
    }


}