package com.eghm.configuration.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * @author 殿小二
 * @since 2020/7/23
 */
public interface InterceptorAdapter extends HandlerInterceptor {

    @Override
    default boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        return this.beforeHandle(request, response, handler);
    }

    /**
     * 前置拦截 默认不做特殊处理
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return default true
     */
    boolean beforeHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException;

    /**
     * 获取handler上方法的指定注解
     *
     * @param handler        handler
     * @param annotationType 注解类型
     * @param <A>            泛型
     * @return 注解信息
     */
    default <A extends Annotation> A getAnnotation(Object handler, Class<A> annotationType) {
        return ((HandlerMethod) handler).getMethodAnnotation(annotationType);
    }

    /**
     * 获取handler上的指定注解
     *
     * @param handler        handler
     * @param annotationType 注解类型
     * @param <A>            泛型
     * @return 注解信息
     */
    default <A extends Annotation> A getClassAnnotation(Object handler, Class<A> annotationType) {
        return ((HandlerMethod) handler).getBeanType().getAnnotation(annotationType);
    }
}
