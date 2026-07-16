package com.eghm.platform.job.config.task.job;

import com.eghm.foundation.core.annotation.CronMark;
import com.eghm.platform.job.config.task.config.OnceScheduleBean;
import com.eghm.platform.job.config.task.config.TaskRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/9/6 16:29
 */
@Slf4j
@Service("testJobService")
public class TestJobService {

    private TaskRegistrar taskRegistrar;

    @Autowired(required = false)
    public void setSysTaskRegistrar(TaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    @CronMark
    public void execute(String args) {
        log.error("我是个数据库配置的Job, 我的作用是触发一个30s后执行的一次性任务 [{}] [{}]", args, LocalDateTime.now());
        OnceScheduleBean onceDetail = new OnceScheduleBean();
        onceDetail.setBeanName("onceJobService");
        onceDetail.setMethodName("execute");
        onceDetail.setArgs("一次性任务入参");
        onceDetail.setExecuteTime(LocalDateTime.now().plusSeconds(30));
        taskRegistrar.addTask(onceDetail);
    }

    @Scheduled(cron = "0 0 0-5 * * ?")
    public void annotationTest() {
        log.error("我是个注解配置的Job {}", LocalDateTime.now());
    }
}
