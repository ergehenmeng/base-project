package com.eghm.handler.chain.annotation;

import com.eghm.handler.chain.enums.HandlerEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 殿小二
 * @since 2021/3/26
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface HandlerMark {

    /**
     * 执行链分类标示符
     *
     * @return 分类标示符
     */
    HandlerEnum value();
}
