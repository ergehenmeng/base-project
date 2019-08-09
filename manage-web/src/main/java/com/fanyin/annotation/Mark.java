package com.fanyin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 操作日志注解 只有被注释才会保存到数据库
 * @author 二哥很猛
 * @date 2019/1/15 16:15
 */
@Documented
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface Mark {
    /**
     * 操作日志类型
     * @return 默认复合操作
     */
    RequestType value() default RequestType.ALL;

    /**
     * 是否保存请求参数
     * @return true
     */
    boolean request() default true;

    /**
     * 是否保存响应参数
     * @return true
     */
    boolean response() default false;
}
