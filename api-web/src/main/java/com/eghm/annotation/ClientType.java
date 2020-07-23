package com.eghm.annotation;


import com.eghm.common.enums.Channel;
import com.eghm.interceptor.ClientTypeInterceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 限制某个方法的客户端访问类型 在Controller方法中使用
 *
 * @see ClientTypeInterceptor 拦截规则
 * @author 二哥很猛
 * @date 2018/8/14 16:19
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface ClientType {

    /**
     * 接口访问类型限制
     *
     * @return 默认后台请求
     */
    Channel[] value() default {};

}
