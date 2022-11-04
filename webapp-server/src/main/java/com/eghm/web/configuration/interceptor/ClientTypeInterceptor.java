package com.eghm.web.configuration.interceptor;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.enums.Channel;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.web.annotation.ClientType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 二哥很猛
 * @date 2019/11/21 10:00
 */
public class ClientTypeInterceptor implements InterceptorAdapter {

    /**
     * 默认只允许 ios和android的设备访问接口
     */
    private static final Channel[] DEFAULT_CHANNEL = {Channel.IOS, Channel.ANDROID};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!supportHandler(handler)) {
            return true;
        }
        String channel = request.getHeader(AppHeader.CHANNEL);
        Channel[] channels = getClientTypeAnnotation(handler);
        for (Channel ch : channels) {
            if (ch.name().equals(channel)) {
                return true;
            }
        }
        throw new ParameterException(ErrorCode.REQUEST_INTERFACE_ERROR);
    }

    /**
     * 获取handlerMethod上指定类型的注解,如果类上有也会返回
     *
     * @param handler handlerMethod
     * @return ClientType
     */
    private Channel[] getClientTypeAnnotation(Object handler) {
        ClientType clientType = this.getAnnotation(handler, ClientType.class);
        return clientType != null ? clientType.value() : DEFAULT_CHANNEL;
    }
}
