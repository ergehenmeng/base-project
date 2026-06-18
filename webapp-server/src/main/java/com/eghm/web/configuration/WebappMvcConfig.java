package com.eghm.web.configuration;

import com.eghm.cache.CacheProxyService;
import com.eghm.common.MemberTokenService;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.configuration.WebMvcConfig;
import com.eghm.configuration.version.ApiVersionRequestMappingHandlerMapping;
import com.eghm.constants.CommonConstant;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.web.configuration.filter.ByteHttpRequestFilter;
import com.eghm.web.configuration.filter.IpBlackListFilter;
import com.eghm.web.configuration.interceptor.ApiSignInterceptor;
import com.eghm.web.configuration.interceptor.ApiVersionInterceptor;
import com.eghm.web.configuration.interceptor.MessageInterceptor;
import com.eghm.web.configuration.interceptor.SubmitIntervalInterceptor;
import com.eghm.web.configuration.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;

import static com.eghm.constants.CommonConstant.FILTER_PATTERN;
import static com.eghm.constants.CommonConstant.INTERCEPTOR_PATTERN;
import static com.eghm.constants.CommonConstant.WEBAPP_PREFIX;

/**
 * mvc全局配置,继承WebMvcConfigurerAdapter无需@EnableWebMvc
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:35
 */
@Configuration
public class WebappMvcConfig extends WebMvcConfig implements WebMvcRegistrations {
    
    private final CacheProxyService cacheProxyService;
    
    private final MemberTokenService memberTokenService;
    
    public WebappMvcConfig(ObjectMapper objectMapper, ApplicationProperties applicationProperties, MemberTokenService memberTokenService, CacheProxyService cacheProxyService, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        super(objectMapper, taskExecutor, applicationProperties);
        this.cacheProxyService = cacheProxyService;
        this.memberTokenService = memberTokenService;
    }
    
    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        String[] notifyUrl = new String[]{CommonConstant.ALI_PAY_NOTIFY_URL, CommonConstant.ALI_REFUND_NOTIFY_URL, CommonConstant.WECHAT_PAY_NOTIFY_URL, CommonConstant.WECHAT_REFUND_NOTIFY_URL};
        registry.addInterceptor(messageInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).order(Integer.MIN_VALUE + 5);
        registry.addInterceptor(apiSignInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).order(Integer.MIN_VALUE + 10);
        registry.addInterceptor(tokenInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 15);
        registry.addInterceptor(submitIntervalInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 30);
        registry.addInterceptor(apiVersionInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).order(Integer.MIN_VALUE + 35);
    }
    
    /**
     * 登陆校验拦截器
     */
    @Bean
    public HandlerInterceptor tokenInterceptor() {
        return new TokenInterceptor(memberTokenService);
    }
    
    /**
     * 提交间隔限制
     */
    @Bean
    public HandlerInterceptor submitIntervalInterceptor() {
        return new SubmitIntervalInterceptor();
    }
    
    /**
     * 请求基础信息收集拦截器
     */
    @Bean
    public HandlerInterceptor messageInterceptor() {
        return new MessageInterceptor();
    }
    
    /**
     * 请求基础信息收集拦截器
     */
    @Bean
    public HandlerInterceptor apiSignInterceptor() {
        return new ApiSignInterceptor(cacheProxyService);
    }
    
    /**
     * API版本拦截器
     */
    @Bean
    public HandlerInterceptor apiVersionInterceptor() {
        return new ApiVersionInterceptor();
    }
    
    /**
     * API版本路由处理器
     * 通过WebMvcRegistrations接口替代默认的RequestMappingHandlerMapping
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        ApiVersionRequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }
    
    /**
     * 过滤器,由spring管理
     */
    @Bean
    public FilterRegistrationBean<IpBlackListFilter> ipBlackListFilter(BlackRosterService blackRosterService) {
        FilterRegistrationBean<IpBlackListFilter> registrationBean = new FilterRegistrationBean<>();
        IpBlackListFilter requestFilter = new IpBlackListFilter(blackRosterService);
        registrationBean.setFilter(requestFilter);
        registrationBean.addUrlPatterns(WEBAPP_PREFIX + FILTER_PATTERN);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }
    
    /**
     * 过滤器,由spring管理
     */
    @Bean
    public FilterRegistrationBean<ByteHttpRequestFilter> byteHttpRequestFilter() {
        FilterRegistrationBean<ByteHttpRequestFilter> registrationBean = new FilterRegistrationBean<>();
        ByteHttpRequestFilter requestFilter = new ByteHttpRequestFilter();
        registrationBean.setFilter(requestFilter);
        registrationBean.addUrlPatterns(WEBAPP_PREFIX + FILTER_PATTERN);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

}