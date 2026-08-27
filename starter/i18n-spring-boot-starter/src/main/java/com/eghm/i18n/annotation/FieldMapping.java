package com.eghm.i18n.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标记需要国际化翻译的响应体类
 * 使用此注解后，响应体的 msg 字段会被自动翻译
 *
 * @author wyb-eghm
 * @since 2026/5/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FieldMapping {
    
    /**
     * 错误码字段名，默认 "code"
     */
    String code() default "code";
    
    /**
     * 消息字段名，默认 "msg"
     */
    String msg() default "msg";
    
    /**
     * 成功码，默认 200，成功码不进行翻译
     */
    int success() default 200;
    
    /**
     * 字典 key，默认 "error_code"
     */
    String key() default "error_code";
}
