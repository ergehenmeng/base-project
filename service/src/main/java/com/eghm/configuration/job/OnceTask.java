package com.eghm.configuration.job;

import lombok.Data;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:36
 */
@Data
public class OnceTask {

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
