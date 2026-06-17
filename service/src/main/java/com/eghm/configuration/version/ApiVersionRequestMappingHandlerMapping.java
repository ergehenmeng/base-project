package com.eghm.configuration.version;

import com.eghm.annotation.ApiVersion;
import jakarta.annotation.Nonnull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * API版本路由处理器
 * 根据@ApiVersion注解和请求头中的版本号进行路由
 *
 * @author eghm
 * @since 2025/6/15
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    
    @Override
    protected RequestCondition<?> getCustomTypeCondition(@Nonnull Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }
    
    @Override
    protected RequestCondition<?> getCustomMethodCondition(@Nonnull Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }
    
    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        if (apiVersion == null) {
            return null;
        }
        return new ApiVersionCondition(apiVersion.value());
    }
}
