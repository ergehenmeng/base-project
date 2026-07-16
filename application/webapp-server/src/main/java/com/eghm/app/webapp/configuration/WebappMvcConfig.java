package com.eghm.app.webapp.configuration;

import com.eghm.platform.iam.service.AuthConfigCacheService;
import com.eghm.member.account.service.MemberTokenService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.web.config.WebMvcConfig;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.platform.config.service.BlackRosterService;
import com.eghm.app.webapp.configuration.filter.ByteHttpRequestFilter;
import com.eghm.app.webapp.configuration.filter.IpBlackListFilter;
import com.eghm.app.webapp.configuration.interceptor.ApiSignInterceptor;
import com.eghm.app.webapp.configuration.interceptor.MessageInterceptor;
import com.eghm.app.webapp.configuration.interceptor.SubmitIntervalInterceptor;
import com.eghm.app.webapp.configuration.interceptor.TokenInterceptor;
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

import static com.eghm.foundation.core.constants.CommonConstant.FILTER_PATTERN;
import static com.eghm.foundation.core.constants.CommonConstant.INTERCEPTOR_PATTERN;
import static com.eghm.foundation.core.constants.CommonConstant.WEBAPP_PREFIX;

/**
 * mvc全局配置,继承WebMvcConfigurerAdapter无需@EnableWebMvc
 *
 * @author 二哥很猛
 * @since 2018/1/18 18:35
 */
@Configuration
public class WebappMvcConfig extends WebMvcConfig {
    
    private final AuthConfigCacheService authConfigCacheService;
    
    private final MemberTokenService memberTokenService;
    
    public WebappMvcConfig(ObjectMapper objectMapper, ApplicationProperties applicationProperties, MemberTokenService memberTokenService, AuthConfigCacheService authConfigCacheService, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        super(objectMapper, taskExecutor, applicationProperties);
        this.authConfigCacheService = authConfigCacheService;
        this.memberTokenService = memberTokenService;
    }
    
    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        String[] notifyUrl = new String[]{CommonConstant.ALI_PAY_NOTIFY_URL, CommonConstant.ALI_REFUND_NOTIFY_URL, CommonConstant.WECHAT_PAY_NOTIFY_URL, CommonConstant.WECHAT_REFUND_NOTIFY_URL};
        registry.addInterceptor(messageInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).order(Integer.MIN_VALUE + 5);
        registry.addInterceptor(apiSignInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).order(Integer.MIN_VALUE + 10);
        registry.addInterceptor(tokenInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 15);
        registry.addInterceptor(submitIntervalInterceptor()).addPathPatterns(WEBAPP_PREFIX + INTERCEPTOR_PATTERN).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 30);
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
        return new ApiSignInterceptor(authConfigCacheService);
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
