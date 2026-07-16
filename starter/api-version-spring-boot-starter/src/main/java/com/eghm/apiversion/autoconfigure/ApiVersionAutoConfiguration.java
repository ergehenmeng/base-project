package com.eghm.apiversion.autoconfigure;

import com.eghm.apiversion.config.ApiVersionProperties;
import com.eghm.apiversion.interceptor.ApiVersionInterceptor;
import com.eghm.apiversion.mvc.ApiVersionRequestMappingHandlerMapping;
import com.eghm.apiversion.spi.ApiVersionComparator;
import com.eghm.apiversion.spi.ApiVersionResolver;
import com.eghm.apiversion.support.HeaderApiVersionResolver;
import com.eghm.apiversion.support.NumericApiVersionComparator;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@AutoConfiguration
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@ConditionalOnClass(RequestMappingHandlerMapping.class)
@EnableConfigurationProperties(ApiVersionProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "application.api-version", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ApiVersionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiVersionResolver apiVersionResolver(ApiVersionProperties properties) {
        return new HeaderApiVersionResolver(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiVersionComparator apiVersionComparator() {
        return new NumericApiVersionComparator();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiVersionInterceptor apiVersionInterceptor(ApiVersionProperties properties) {
        return new ApiVersionInterceptor(properties);
    }
    
    @Bean
    public WebMvcConfigurer webMvcConfigurer(ApiVersionInterceptor interceptor, ApiVersionProperties properties) {
        
        return new WebMvcConfigurer() {

            @Override
            public void addInterceptors(@Nonnull InterceptorRegistry registry) {
                var registration = registry.addInterceptor(interceptor) .addPathPatterns(properties.getPathPatterns()).order(properties.getOrder());
                if (!properties.getExcludePathPatterns().isEmpty()) {
                    registration.excludePathPatterns(properties.getExcludePathPatterns());
                }
            }
        };
    }

    /**
     * 注册支持 {@link com.eghm.apiversion.annotation.ApiVersion ApiVersion}
     * 条件的请求映射处理器。
     *
     * @param versionResolver   客户端版本解析器
     * @param versionComparator 版本比较器
     * @return Spring MVC 组件注册器
     */
    @Bean
    @ConditionalOnMissingBean(WebMvcRegistrations.class)
    public WebMvcRegistrations webMvcRegistrations(ApiVersionResolver versionResolver, ApiVersionComparator versionComparator) {
        
        return new WebMvcRegistrations() {
            
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                ApiVersionRequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping(versionResolver, versionComparator);
                handlerMapping.setOrder(0);
                return handlerMapping;
            }
            
        };
    }
}
