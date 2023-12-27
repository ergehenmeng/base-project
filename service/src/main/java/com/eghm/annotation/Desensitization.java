package com.eghm.annotation;

import com.eghm.enums.FieldType;

import java.lang.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/27
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Desensitization {

    /**
     * 字段类型
     * @return 脱敏字段类型
     */
    FieldType value();
}
