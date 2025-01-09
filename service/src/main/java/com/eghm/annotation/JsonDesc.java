package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonDesc {
}
