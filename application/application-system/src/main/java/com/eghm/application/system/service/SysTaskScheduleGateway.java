package com.eghm.application.system.service;

import java.time.LocalDateTime;

/**
 * 定时任务调度网关
 *
 * @author 二哥很猛
 */
public interface SysTaskScheduleGateway {

    /**
     * 安排一次任务执行
     *
     * @param beanName    bean名称
     * @param methodName  方法名称
     * @param args        执行参数
     * @param executeTime 执行时间
     * @return true:安排成功 false:调度器未启用
     */
    boolean scheduleOnce(String beanName, String methodName, String args, LocalDateTime executeTime);
}
