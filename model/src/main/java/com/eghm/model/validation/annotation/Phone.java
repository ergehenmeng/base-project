package com.eghm.model.validation.annotation;

import com.eghm.model.validation.MobileDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 电话号码校验 只适用字符串
 * @author 二哥很猛
 * @date 2018/8/14 11:39
 */
@Documented
@Constraint(validatedBy = MobileDefine.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Phone {

    /**
     * 错误信息 必须包含该属性
     * @return 错误信息
     */
    String message() default "电话号码格式错误";

    /**
     * 是否必填
     * @return 默认非必填
     */
    boolean required() default true;

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
