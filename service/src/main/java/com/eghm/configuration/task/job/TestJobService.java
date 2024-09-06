package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.CronMark;
import com.eghm.configuration.task.config.OnceTask;
import com.eghm.configuration.task.config.SysTaskRegistrar;
import com.eghm.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/9/6 16:29
 */
@Service("testJobService")
@Slf4j
public class TestJobService {

    private SysTaskRegistrar sysTaskRegistrar;

    @Autowired(required = false)
    public void setSysTaskRegistrar(SysTaskRegistrar sysTaskRegistrar) {
        this.sysTaskRegistrar = sysTaskRegistrar;
    }


    @CronMark
    public void execute(String args) {
        log.error("我是个数据库配置的Job, 我的作用是触发一次性任务 [{}] [{}]", args, LocalDateTime.now());
        OnceTask onceDetail = new OnceTask();
        onceDetail.setBeanName("onceJobService");
        onceDetail.setMethodName("execute");
        onceDetail.setArgs("一次性任务入参");
        onceDetail.setExecuteTime(LocalDateTime.now().plusSeconds(10));
        sysTaskRegistrar.addTask(onceDetail);
    }

    @Scheduled(cron = "0 0 0-5 * * ?")
    public void annotationTest() {
        log.error("我是个注解配置的Job {}", LocalDateTime.now());
    }
}
