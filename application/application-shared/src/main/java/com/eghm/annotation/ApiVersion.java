package com.eghm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.eghm.constants.CommonConstant.DEFAULT_VERSION;

/**
 * API版本注解
 * 用于标记Controller或方法的API版本号
 * 版本号通过请求头 Api-Version 传递
 *
 * @since 2025/6/15
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    
    /**
     * 版本号 1.0.0~99.99.99, 其他格式无效
     * @return 版本号，默认为1.0.0
     */
    String value() default DEFAULT_VERSION;
    
    /**
     * 是否为废弃版本
     * @return true表示该版本已废弃，访问时会返回警告信息
     */
    boolean deprecated() default false;
    
    /**
     * 废弃提示消息
     * @return 废弃时的提示信息
     */
    String deprecatedMessage() default "";
}

