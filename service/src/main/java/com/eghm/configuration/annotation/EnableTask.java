package com.eghm.configuration.annotation;

import com.eghm.configuration.task.config.TaskConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用定时任务功能
 * @author 殿小二
 * @date 2020/8/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TaskConfiguration.class)
@Documented
public @interface EnableTask {
}
