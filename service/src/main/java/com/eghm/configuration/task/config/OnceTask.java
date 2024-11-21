package com.eghm.configuration.task.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2020/1/6 18:36
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OnceTask extends AbstractTask {

    /**
     * 任务执行的时间 (未来的某个时间)
     */
    private LocalDateTime executeTime;

}
