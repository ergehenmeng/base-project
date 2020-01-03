package com.eghm.interceptor;

import com.eghm.annotation.ClientType;
import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.Channel;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 二哥很猛
 * @date 2019/11/21 10:00
 */
public class ClientTypeHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 默认只允许 ios和android的设备访问接口
     */
    private static final Channel[] DEFAULT_CHANNEL = {Channel.IOS, Channel.ANDROID};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String channel = request.getHeader(AppHeader.CHANNEL);
        Channel[] channels = getClientTypeAnnotation(handler);
        for (Channel ch : channels) {
            if (ch.name().equals(channel)) {
                return true;
            }
        }
        throw new BusinessException(ErrorCode.REQUEST_INTERFACE_ERROR);
    }

    /**
     * 获取handlerMethod上指定类型的注解,如果类上有也会返回
     *
     * @param handler handlerMethod
     * @return ClientType
     */
    private Channel[] getClientTypeAnnotation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            ClientType clientType = method.getMethodAnnotation(ClientType.class);
            return clientType != null ? clientType.value() : DEFAULT_CHANNEL;
        }
        return DEFAULT_CHANNEL;
    }
}
