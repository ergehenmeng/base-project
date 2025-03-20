package com.eghm.annotation;

import com.eghm.excel.DynamicSpinner;

import java.lang.annotation.*;

/**
 * @author 二哥很猛
 * @since 2025/3/18
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSpinner {

    /**
     * 固定的下拉选项
     */
    String[] source() default {};

    /**
     * @return 动态下拉选项
     */
    Class<? extends DynamicSpinner>[] sourceClass() default {};

    /**
     * @return 数据字典key
     */
    String value() default "";

    /**
     * 第二行开始显示下拉列表
     */
    int start() default 1;

    /**
     * 最多显示的行数
     */
    int end() default 65536;

}
