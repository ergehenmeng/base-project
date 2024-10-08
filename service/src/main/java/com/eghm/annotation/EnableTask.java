package com.eghm.annotation;

import com.eghm.configuration.task.config.TaskConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用定时任务
 *
 * @author 殿小二
 * @since 2020/8/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TaskConfig.class)
@Documented
public @interface EnableTask {
}
