package com.eghm.validation.annotation;

import com.eghm.enums.Channel;
import com.eghm.validation.ChannelTypeDefine;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 客户端类型校验 只适用string
 *
 * @author 二哥很猛
 * @since 2018/8/14 16:01
 */
@Documented
@Constraint(validatedBy = ChannelTypeDefine.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface ChannelType {

    /**
     * 错误信息 必须包含该属性
     *
     * @return 错误信息
     */
    String message() default "非法参数";

    /**
     * 客户端类型
     *
     * @return 默认pc
     */
    Channel[] value() default Channel.PC;

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
