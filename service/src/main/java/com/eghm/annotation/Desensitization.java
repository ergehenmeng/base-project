package com.eghm.annotation;

import com.eghm.enums.FieldType;

import java.lang.annotation.*;

/**
 * 脱敏注解, 用于标记需要脱敏的字段(序列化返回前端json时使用)
 *
 * @author 二哥很猛
 * @since 2023/12/27
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Desensitization {

    /**
     * 字段类型
     *
     * @return 脱敏字段类型
     */
    FieldType value();
}
