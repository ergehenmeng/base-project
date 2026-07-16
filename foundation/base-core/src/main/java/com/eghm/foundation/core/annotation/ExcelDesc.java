package com.eghm.foundation.core.annotation;

import java.lang.annotation.*;

/**
 * 导出excel枚举注解
 * 用于需要显示到Excel的枚举字段
 *
 * @author wyb
 * @since 2023/4/3
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDesc {
}
