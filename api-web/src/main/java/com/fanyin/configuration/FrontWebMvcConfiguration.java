package com.fanyin.configuration;

import com.fanyin.configuration.filter.ByteHttpRequestFilter;
import com.fanyin.interceptor.AccessTokenHandlerInterceptor;
import com.fanyin.interceptor.JsonHandlerMethodArgumentResolver;
import com.fanyin.interceptor.MessageHandlerInterceptor;
import com.fanyin.interceptor.SignatureHandlerInterceptor;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

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


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signatureHandlerInterceptor()).order(Integer.MIN_VALUE + 1);
        registry.addInterceptor(messageHandlerInterceptor()).order(Integer.MIN_VALUE + 2);
        registry.addInterceptor(accessHandlerInterceptor()).order(Integer.MIN_VALUE + 3);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonHandlerMethodArgumentResolver());
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
    public HandlerInterceptor accessHandlerInterceptor(){
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
