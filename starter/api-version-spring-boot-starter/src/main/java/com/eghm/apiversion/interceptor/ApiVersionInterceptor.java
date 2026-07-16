package com.eghm.apiversion.interceptor;

import com.eghm.apiversion.annotation.ApiVersion;
import com.eghm.apiversion.config.ApiVersionProperties;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 为已废弃的 API 版本写入响应提示头的 Spring MVC 拦截器。
 *
 * @since 2025/6/15
 */
public class ApiVersionInterceptor implements HandlerInterceptor {

    private final ApiVersionProperties properties;

    public ApiVersionInterceptor(ApiVersionProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        ApiVersion apiVersion = handlerMethod.getMethodAnnotation(ApiVersion.class);
        if (apiVersion == null) {
            apiVersion = handlerMethod.getBeanType().getAnnotation(ApiVersion.class);
        }
        if (apiVersion != null && apiVersion.deprecated()) {
            response.setHeader(properties.getDeprecatedHeaderName(), "true");
            if (apiVersion.deprecatedMessage() != null && !apiVersion.deprecatedMessage().isEmpty()) {
                response.setHeader(properties.getDeprecatedMessageHeaderName(), apiVersion.deprecatedMessage());
            }
        }
        return true;
    }
}
