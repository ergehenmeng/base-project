package com.fanyin.annotation;

import com.fanyin.interceptor.JsonExtractHandlerArgumentResolver;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 前台传递过来的参数默认会被解析为application/json,如果不需要解析为json则需要添加该注解 例如文件上传等
 * @see JsonExtractHandlerArgumentResolver#supportsParameter(MethodParameter)  拦截规则
 * @author 二哥很猛
 * @date 2018/8/15 17:55
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipDataBinder {

}
