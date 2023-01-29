package com.eghm.configuration.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;

/**
 * @author 殿小二
 * @date 2020/7/23
 */
public interface InterceptorAdapter extends HandlerInterceptor {

    /**
     * handler是否为 HandlerMethod
     * @param handler  handler
     * @return true:是 false:否
     */
    default boolean supportHandler(Object handler) {
        return handler instanceof HandlerMethod;
    }

    /**
     * 获取handler上方法的指定注解
     * @param handler  handler
     * @param annotationType 注解类型
     * @param <A> 泛型
     * @return 注解信息
     */
    default <A extends Annotation> A getAnnotation(Object handler, Class<A> annotationType) {
        return ((HandlerMethod) handler).getMethodAnnotation(annotationType);
    }

    /**
     * 获取handler上的指定注解
     * @param handler  handler
     * @param annotationType 注解类型
     * @param <A> 泛型
     * @return 注解信息
     */
    default <A extends Annotation> A getClassAnnotation(Object handler, Class<A> annotationType) {
        return ((HandlerMethod) handler).getBeanType().getAnnotation(annotationType);
    }
}
