package com.eghm.apiversion.mvc;

import com.eghm.apiversion.annotation.ApiVersion;
import com.eghm.apiversion.condition.ApiVersionCondition;
import com.eghm.apiversion.spi.ApiVersionComparator;
import com.eghm.apiversion.spi.ApiVersionResolver;
import jakarta.annotation.Nonnull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 将 {@link ApiVersion} 转换为 Spring MVC 自定义请求条件的处理器映射。
 *
 * @since 2025/6/15
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final ApiVersionResolver versionResolver;
    
    private final ApiVersionComparator versionComparator;

    public ApiVersionRequestMappingHandlerMapping(ApiVersionResolver versionResolver, ApiVersionComparator versionComparator) {
        this.versionResolver = versionResolver;
        this.versionComparator = versionComparator;
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(@Nonnull Class<?> handlerType) {
        return this.createCondition(AnnotationUtils.findAnnotation(handlerType, ApiVersion.class));
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(@Nonnull Method method) {
        return this.createCondition(AnnotationUtils.findAnnotation(method, ApiVersion.class));
    }

    /**
     * 将版本注解转换为请求条件。
     *
     * @param apiVersion API 版本注解
     * @return API 版本请求条件；注解不存在时返回 {@code null}
     */
    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        if (apiVersion == null) {
            return null;
        }
        return new ApiVersionCondition(apiVersion.value(), versionResolver, versionComparator);
    }
}
