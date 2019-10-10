package com.fanyin.configuration;

import com.fanyin.configuration.filter.ByteHttpRequestFilter;
import com.fanyin.interceptor.*;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
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
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
public class FrontWebMvcConfiguration extends WebMvcConfiguration {

    private static final String[] EXCLUDE_URL = {"/swagger/**"};

    /**
     * 移动端请求地址
     */
    private static final String[] MOBILE_URL = {"/api/**"};

    /**
     * 小程序端请求地址
     */
    private static final String[] APPLET_URL = {"/applet/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipHandlerInterceptor()).order(Integer.MIN_VALUE + 5);
        registry.addInterceptor(signatureHandlerInterceptor()).addPathPatterns(MOBILE_URL).order(Integer.MIN_VALUE + 10);
        registry.addInterceptor(messageHandlerInterceptor()).addPathPatterns(MOBILE_URL).order(Integer.MIN_VALUE + 15);
        registry.addInterceptor(accessTokenHandlerInterceptor()).addPathPatterns(MOBILE_URL).order(Integer.MIN_VALUE + 20);
        registry.addInterceptor(ssoHandlerInterceptor()).addPathPatterns(MOBILE_URL).order(Integer.MIN_VALUE + 25);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath*:/swagger/dist/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonHandlerMethodArgumentResolver());
    }

    @Bean
    public HandlerInterceptor ssoHandlerInterceptor(){
        return new SsoHandlerInterceptor();
    }

    @Bean
    public JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver(){
        return new JsonHandlerMethodArgumentResolver();
    }

    /**
     * 登陆校验拦截器
     * @return com.fanyin.interceptor
     */
    @Bean
    public HandlerInterceptor accessTokenHandlerInterceptor(){
        return new AccessTokenHandlerInterceptor();
    }

    /**
     * 签名校验拦截器
     * @return com.fanyin.interceptor
     */
    @Bean
    public HandlerInterceptor signatureHandlerInterceptor(){
        return new SignatureHandlerInterceptor();
    }

    @Bean
    public HandlerInterceptor ipHandlerInterceptor(){
        return new IpHandlerInterceptor();
    }

    /**
     * 请求基础信息收集拦截器
     * @return com.fanyin.interceptor
     */
    @Bean
    public HandlerInterceptor messageHandlerInterceptor(){
        return new MessageHandlerInterceptor();
    }


    @Bean("byteHttpRequestFilter")
    public Filter byteHttpRequestFilter(){
        ByteHttpRequestFilter requestFilter = new ByteHttpRequestFilter();
        requestFilter.getExclude().add("/upload/**");
        requestFilter.getExclude().add("/swagger/**");
        return requestFilter;
    }

    @Bean
    public DelegatingFilterProxyRegistrationBean registrationBean(){
        DelegatingFilterProxyRegistrationBean registrationBean = new DelegatingFilterProxyRegistrationBean("byteHttpRequestFilter");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }
}
