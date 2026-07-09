package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * 标识符, 用来标识该方法是定时任务方法
 *
 * @author 二哥很猛
 * @since 2023/9/24
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface CronMark {
}
