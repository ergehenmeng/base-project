package com.eghm.infrastructure.persistence.schedule;

import com.eghm.infrastructure.persistence.schedule.config.SchedulingConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用定时任务
 *
 * @author 殿小二
 * @since 2020/8/14
 */
@Documented
@Target(ElementType.TYPE)
@Import(SchedulingConfig.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableSchedulingTask {
}
