package com.eghm.web.configuration;

import com.eghm.cache.CacheProxyService;
import com.eghm.common.MemberTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.WebMvcConfig;
import com.eghm.constants.CommonConstant;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.web.configuration.filter.ByteHttpRequestFilter;
import com.eghm.web.configuration.filter.IpBlackListFilter;
import com.eghm.web.configuration.interceptor.AccessSignInterceptor;
import com.eghm.web.configuration.interceptor.MessageInterceptor;
import com.eghm.web.configuration.interceptor.SubmitIntervalInterceptor;
import com.eghm.web.configuration.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * mvc全局配置,继承WebMvcConfigurerAdapter无需@EnableWebMvc
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:35
 */
@Configuration
public class WebappMvcConfig extends WebMvcConfig {

    private final CacheProxyService cacheProxyService;

    private final MemberTokenService memberTokenService;
    
    private static final String FILTER_URL = "/webapp/*";
    
    private static final String INTERCEPTOR_URL = "/webapp/**";

    public WebappMvcConfig(ObjectMapper objectMapper, SystemProperties systemProperties, MemberTokenService memberTokenService, CacheProxyService cacheProxyService, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        super(objectMapper, taskExecutor, systemProperties);
        this.cacheProxyService = cacheProxyService;
        this.memberTokenService = memberTokenService;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        String[] notifyUrl = new String[]{CommonConstant.ALI_PAY_NOTIFY_URL, CommonConstant.ALI_REFUND_NOTIFY_URL, CommonConstant.WECHAT_PAY_NOTIFY_URL, CommonConstant.WECHAT_REFUND_NOTIFY_URL};
        registry.addInterceptor(messageInterceptor()).addPathPatterns(INTERCEPTOR_URL).order(Integer.MIN_VALUE + 5);
        registry.addInterceptor(accessSignInterceptor()).addPathPatterns(INTERCEPTOR_URL).order(Integer.MIN_VALUE + 10);
        registry.addInterceptor(tokenInterceptor()).addPathPatterns(INTERCEPTOR_URL).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 15);
        registry.addInterceptor(submitIntervalInterceptor()).addPathPatterns(INTERCEPTOR_URL).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 30);
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
    public HandlerInterceptor accessSignInterceptor() {
        return new AccessSignInterceptor(cacheProxyService);
    }
    
    /**
     * 过滤器,由spring管理
     */
    @Bean
    public FilterRegistrationBean<IpBlackListFilter> ipBlackListFilter(BlackRosterService blackRosterService) {
        FilterRegistrationBean<IpBlackListFilter> registrationBean = new FilterRegistrationBean<>();
        IpBlackListFilter requestFilter = new IpBlackListFilter(blackRosterService);
        registrationBean.setFilter(requestFilter);
        registrationBean.addUrlPatterns(FILTER_URL);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }

    /**
     * 过滤器,由spring管理
     */
    @Bean("byteHttpRequestFilter")
    public FilterRegistrationBean<ByteHttpRequestFilter> byteHttpRequestFilter() {
        FilterRegistrationBean<ByteHttpRequestFilter> registrationBean = new FilterRegistrationBean<>();
        ByteHttpRequestFilter requestFilter = new ByteHttpRequestFilter();
        registrationBean.setFilter(requestFilter);
        registrationBean.addUrlPatterns(FILTER_URL);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

}
