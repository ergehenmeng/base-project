package com.eghm.web.configuration.interceptor;

import com.eghm.annotation.ApiVersion;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.constants.AppHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * API版本拦截器
 * 用于处理废弃版本的警告信息
 *
 * @since 2026/6/15
 */
public class ApiVersionInterceptor implements InterceptorAdapter {
    
    @Override
    public boolean beforeHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ApiVersion apiVersion = getAnnotation(handler, ApiVersion.class);
        if (apiVersion != null && apiVersion.deprecated()) {
            response.setHeader(AppHeader.API_DEPRECATED, "true");
            if (apiVersion.deprecatedMessage() != null && !apiVersion.deprecatedMessage().isEmpty()) {
                response.setHeader(AppHeader.API_DEPRECATED_MESSAGE, apiVersion.deprecatedMessage());
            }
        }
        return true;
    }
    
}
