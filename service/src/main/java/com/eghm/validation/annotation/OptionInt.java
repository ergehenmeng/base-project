package com.eghm.validation.annotation;


import com.eghm.validation.OptionIntDefine;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 参数范围拦截 只适用int
 *
 * @author 二哥很猛
 * @since 2018/8/14 13:40
 */
@Documented
@Constraint(validatedBy = OptionIntDefine.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface OptionInt {

    /**
     * 错误信息 必须包含该属性
     *
     * @return 错误信息
     */
    String message() default "非法参数";

    /**
     * 取值列表
     *
     * @return 列表
     */
    int[] value() default {};

    /**
     * 是否必填
     *
     * @return 默认非必填
     */
    boolean required() default true;

    /**
     * 自定义校验必须包含该属性
     *
     * @return 自定义校验必须包含该属性
     */
    Class<?>[] groups() default {};

    /**
     * 自定义校验必须包含该属性
     *
     * @return 自定义校验必须包含该属性
     */
    Class<? extends Payload>[] payload() default {};

}

