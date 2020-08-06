package com.eghm.configuration.task.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnceDetail {

    /**
     * 任务标示符
     */
    private String nid;

    /**
     * 执行任务的bean的名称 必须实现Task接口
     */
    private String beanName;

    /**
     * 任务执行的时间
     */
    private Date executeTime;

}
