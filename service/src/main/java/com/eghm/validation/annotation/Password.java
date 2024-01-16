package com.eghm.validation.annotation;

import com.eghm.validation.PasswordDefine;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 密码校验, 默认是8~16英文,字母和下划线
 *
 * @author 二哥很猛
 * @date 2023/12/14 19:19
 */
@Documented
@Constraint(validatedBy = PasswordDefine.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Password {

    /**
     * 错误信息 必须包含该属性
     *
     * @return 错误信息
     */
    String message() default "密码必须包含英文字符、数字、@#&_";

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
