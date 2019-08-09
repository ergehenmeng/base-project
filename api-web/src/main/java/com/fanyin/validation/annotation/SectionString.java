package com.fanyin.validation.annotation;

import com.fanyin.validation.SectionStringDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字符串限制
 * @author 二哥很猛
 * @date 2018/11/9 10:35
 */
@Documented
@Constraint(validatedBy = SectionStringDefine.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface SectionString {

    /**
     * 错误信息 必须包含该属性
     * @return 错误信息
     */
    String message() default "非法参数";

    /**
     * 取值列表
     * @return 列表
     */
    String[] value() default {};

    /**
     * 是否必填
     * @return 默认非必填
     */
    boolean required() default false;

    /**
     * 自定义校验必须包含该属性
     * @return 自定义校验必须包含该属性
     */
    Class<?>[] groups() default {};

    /**
     * 自定义校验必须包含该属性
     * @return 自定义校验必须包含该属性
     */
    Class<? extends Payload>[] payload() default {};
}
