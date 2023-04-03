package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * @author wyb
 * @since 2023/4/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelValue {
}
