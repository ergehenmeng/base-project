package com.eghm.configuration.annotation;

import java.lang.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/10/8
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface YuanToCentFormat {
}
