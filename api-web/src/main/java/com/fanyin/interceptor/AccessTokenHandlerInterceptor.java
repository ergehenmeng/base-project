package com.fanyin.interceptor;

import com.fanyin.annotation.Access;
import com.fanyin.common.constant.HeaderConstant;
import com.fanyin.common.enums.Channel;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.RequestException;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.model.ext.DataMessage;
import com.fanyin.service.common.AccessTokenService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆令牌验证,主要用于app,pc等前后端分离
 * @author 二哥很猛
 * @date 2018/1/23 12:02
 */
@Slf4j
public class AccessTokenHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        DataMessage message = MessageHandlerInterceptor.getMessage();

        //访问来源
        if(!requestType(handler,message.getRequestType())){
            log.error("请求接口非法,requestType:[{}]",message.getRequestType());
            throw new RequestException(ErrorCodeEnum.REQUEST_INTERFACE_ERROR);
        }
        //登陆
        if(access(handler)){
            String accessKey = request.getHeader(HeaderConstant.ACCESS_KEY);
            String accessToken = request.getHeader(HeaderConstant.ACCESS_TOKEN);
            this.accessTokenVerify(accessKey,accessToken,message);
        }
        return true;
    }


    /**
     * 登陆校验
     * @param accessKey accessKey
     * @param accessToken 令牌
     * @param message 存放用户信息的对象
     */
    private void accessTokenVerify(String accessKey,String accessToken,DataMessage message){
        if (accessKey == null || accessToken == null){
            log.error("令牌为空,accessKey:[{}],accessToken:[{}]",accessKey,accessToken);
            throw new RequestException(ErrorCodeEnum.REQUEST_PARAM_ILLEGAL);
        }
        AccessToken token = accessTokenService.getByAccessKey(accessKey);
        if (token == null || !accessToken.equals(token.getAccessToken()) || !token.getRequestType().equals(message.getRequestType())){
            log.error("令牌无效,accessKey:[{}]",accessKey);
            throw new RequestException(ErrorCodeEnum.ACCESS_TOKEN_TIMEOUT);
        }
        //重新放入刷新超时时间
        accessTokenService.saveByAccessKey(token);
        //由于ThreadLocal是对象引用,可以直接设置附加值
        message.setAccessKey(token.getAccessKey());
        message.setAccessToken(token.getAccessToken());
        message.setUserId(token.getUserId());
    }


    /**
     * 是否需要登陆校验
     * @param handler handlerMethod
     * @return true:需要验签 false不需要
     */
    private boolean access(Object handler){
        Access accessToken = this.getAccessAnnotation(handler);
        if(accessToken != null){
            return accessToken.access();
        }
        return false;
    }

    /**
     * 请求类型判断
     * @param handler 处理器
     * @param requestType 请求类型 header传递过来
     * @return 是否为合法请求
     */
    private boolean requestType(Object handler,String requestType){
        Access accessToken = this.getAccessAnnotation(handler);
        if(accessToken != null){
            Channel[] value = accessToken.value();
            return Lists.newArrayList(value).stream().anyMatch(source -> source.name().equals(requestType));
        }
        return true;
    }


    /**
     * 获取handlerMethod上指定类型的注解,如果类上有也会返回
     * @param handler handlerMethod
     * @return AccessToken
     */
    private Access getAccessAnnotation(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod)handler;
            Access t = method.getMethodAnnotation(Access.class);
            return t != null ? t : method.getBeanType().getAnnotation(Access.class);
        }
        return null;
    }


}
