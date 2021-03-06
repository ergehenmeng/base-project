package com.eghm.configuration.task.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:36
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OnceDetail extends TaskDetail{

    /**
     * 任务执行的时间 (未来的某个时间)
     */
    private Date executeTime;

}
