package com.eghm.web.configuration;

import com.eghm.configuration.WebMvcConfiguration;
import com.eghm.web.configuration.filter.ByteHttpRequestFilter;
import com.eghm.web.configuration.filter.IpBlackListFilter;
import com.eghm.web.configuration.interceptor.ClientTypeInterceptor;
import com.eghm.web.configuration.interceptor.MessageInterceptor;
import com.eghm.web.configuration.interceptor.SubmitFrequencyLimitInterceptor;
import com.eghm.web.configuration.interceptor.TokenInterceptor;
import com.eghm.web.configuration.resolver.JsonExtractHandlerArgumentResolver;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

/**
 * mvc全局配置,继承WebMvcConfigurerAdapter无需@EnableWebMvc
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
public class FrontWebMvcConfiguration extends WebMvcConfiguration {

    /**
     * 过滤器不拦截的地址
     */
    private static final String[] FILTER_EXCLUDE_URL = {"/swagger/**", "/swagger-resources/**", "/v2/api-docs", "/error", "/resource/**", "/favicon.ico"};


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(clientTypeInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).order(Integer.MIN_VALUE + 6);
//        registry.addInterceptor(messageInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).order(Integer.MIN_VALUE + 10);
//        registry.addInterceptor(tokenInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).order(Integer.MIN_VALUE + 15);
//        registry.addInterceptor(submitFrequencyLimitInterceptor()).excludePathPatterns(FILTER_EXCLUDE_URL).order(Integer.MIN_VALUE + 30);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/dist/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonExtractHandlerArgumentResolver());
    }


    @Bean
    public JsonExtractHandlerArgumentResolver jsonExtractHandlerArgumentResolver() {
        return new JsonExtractHandlerArgumentResolver();
    }

    /**
     * 登陆校验拦截器
     *
     * @return com.eghm.interceptor
     */
    @Bean
    public HandlerInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * 提交间隔限制
     */
    @Bean
    public HandlerInterceptor submitFrequencyLimitInterceptor() {
        return new SubmitFrequencyLimitInterceptor();
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
     * @return com.eghm.interceptor
     */
    @Bean
    public HandlerInterceptor messageInterceptor() {
        return new MessageInterceptor();
    }


    /**
     * ip黑名单
     */
    @Bean("ipBlackListFilter")
    public Filter ipFilter() {
        return new IpBlackListFilter();
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
        ByteHttpRequestFilter requestFilter = new ByteHttpRequestFilter();
        requestFilter.exclude(FILTER_EXCLUDE_URL);
        registrationBean.setFilter(requestFilter);
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE + 5);
        return registrationBean;
    }
}
