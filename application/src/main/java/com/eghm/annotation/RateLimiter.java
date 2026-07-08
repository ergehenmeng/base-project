package com.eghm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流注解
 *
 * @author eghm
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流器名称(必填,用于日志/metrics 标识以及同一限流器跨方法的复用)
     * 同一名称 + 同一 scope 共享一个 RateLimiter 实例
     */
    String value();

    /**
     * 时间窗口内最大访问次数
     */
    int limit() default 10;

    /**
     * 时间窗口(单位:秒)
     */
    int period() default 60;

    /**
     * 限流维度,默认按 IP
     */
    Scope scope() default Scope.IP;

    /**
     * 限流维度
     */
    enum Scope {
        
        /**
         * 按客户端 IP 限流(支持 X-Forwarded-For)
         */
        IP,
        
        /**
         * 按登录用户 ID 限流(未登录降级为 IP)
         */
        USER,
        
        /**
         * 全局限流(所有请求共享同一个计数器)
         */
        GLOBAL
    }
}
