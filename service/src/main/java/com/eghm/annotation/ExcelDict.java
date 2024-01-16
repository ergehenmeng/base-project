package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * @author wyb
 * @since 2023/3/31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelDict {

    /**
     * 数据字典的key
     *
     * @return key
     */
    String value();
}
