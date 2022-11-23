package com.eghm.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 二哥很猛
 * @since 2022/11/23
 */
@Documented
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface SubmitInterval {

    /**
     * 单人两次提交接口间隔
     * @return 单位:毫秒
     */
    long value() default 1000L;
}
