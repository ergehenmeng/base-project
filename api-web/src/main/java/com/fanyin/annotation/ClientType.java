package com.fanyin.annotation;


import com.fanyin.common.enums.Channel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 接口客户端类型限制
 * @author 二哥很猛
 * @date 2018/8/14 16:19
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface ClientType {

    /**
     * 接口访问类型限制
     * @return 默认后台请求
     */
    Channel[] value() default {};

}
