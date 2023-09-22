package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.WebMvcConfig;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.TokenService;
import com.eghm.service.member.LoginService;
import com.eghm.service.sys.BlackRosterService;
import com.eghm.web.configuration.filter.ByteHttpRequestFilter;
import com.eghm.web.configuration.filter.IpBlackListFilter;
import com.eghm.web.configuration.interceptor.ClientTypeInterceptor;
import com.eghm.web.configuration.interceptor.MessageInterceptor;
import com.eghm.web.configuration.interceptor.SubmitIntervalInterceptor;
import com.eghm.web.configuration.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import static com.eghm.constant.CommonConstant.WEBAPP_PREFIX;

/**
 * mvc全局配置,继承WebMvcConfigurerAdapter无需@EnableWebMvc
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
public class WebappMvcConfig extends WebMvcConfig {

    /**
     * 过滤器不拦截的地址
     */
    private static final String[] FILTER_EXCLUDE_URL = {"/swagger/**", "/swagger-resources/**", "/v2/api-docs", "/error", "/resource/**"};

    private final TokenService tokenService;

    private final LoginService loginService;

    private final CacheService cacheService;

    public WebappMvcConfig(ObjectMapper objectMapper, SystemProperties systemProperties, TokenService tokenService, LoginService loginService, CacheService cacheService) {
        super(objectMapper, systemProperties);
        this.tokenService = tokenService;
        this.loginService = loginService;
        this.cacheService = cacheService;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        String[] notifyUrl = new String[] {
                WEBAPP_PREFIX + systemProperties.getWechat().getPayNotifyUrl(),
                WEBAPP_PREFIX + systemProperties.getWechat().getRefundNotifyUrl(),
                WEBAPP_PREFIX + systemProperties.getAliPay().getPayNotifyUrl(),
                WEBAPP_PREFIX + systemProperties.getAliPay().getRefundNotifyUrl()};
        registry.addInterceptor(clientTypeInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 6);
        registry.addInterceptor(messageInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).order(Integer.MIN_VALUE + 10);
        registry.addInterceptor(tokenInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 15);
        registry.addInterceptor(submitIntervalInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).excludePathPatterns(notifyUrl).order(Integer.MIN_VALUE + 30);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/dist/");
    }

    /**
     * 登陆校验拦截器
     */
    @Bean
    public HandlerInterceptor tokenInterceptor() {
        return new TokenInterceptor(tokenService, loginService);
    }

    /**
     * 提交间隔限制
     */
    @Bean
    public HandlerInterceptor submitIntervalInterceptor() {
        return new SubmitIntervalInterceptor(getSystemProperties(), cacheService);
    }

    /**
     * 客户端类型拦截器
     */
    @Bean
    public HandlerInterceptor clientTypeInterceptor() {
        return new ClientTypeInterceptor();
    }

    /**
     * 请求基础信息收集拦截器
     *
     */
    @Bean
    public HandlerInterceptor messageInterceptor() {
        return new MessageInterceptor();
    }

    /**
     * ip黑名单
     */
    @Bean
    public Filter ipBlackListFilter(BlackRosterService blackRosterService) {
        return new IpBlackListFilter(blackRosterService);
    }

    @Bean
    public DelegatingFilterProxyRegistrationBean registrationBean() {
        DelegatingFilterProxyRegistrationBean registrationBean = new DelegatingFilterProxyRegistrationBean("ipBlackListFilter");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }

    /**
     * 过滤器,不由spring管理
     */
    @Bean("byteHttpRequestFilter")
    public FilterRegistrationBean<ByteHttpRequestFilter> byteHttpRequestFilter() {
        FilterRegistrationBean<ByteHttpRequestFilter> registrationBean = new FilterRegistrationBean<>();
        ByteHttpRequestFilter requestFilter = new ByteHttpRequestFilter(systemProperties);
        requestFilter.exclude(FILTER_EXCLUDE_URL);
        registrationBean.setFilter(requestFilter);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }

}
