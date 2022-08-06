package com.eghm.configuration.annotation;

import java.lang.annotation.*;

/**
 * 标签 仅仅证明标示的方法是定时任务, 且由数据库 `task_config` 来配置触发周期
 * @author 二哥很猛
 * @date 2022/8/6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ScheduledTask {
}
