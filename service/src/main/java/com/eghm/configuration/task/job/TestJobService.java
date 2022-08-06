package com.eghm.configuration.task.job;

import com.eghm.common.utils.DateUtil;
import com.eghm.configuration.annotation.ScheduledTask;
import com.eghm.configuration.task.config.OnceDetail;
import com.eghm.configuration.task.config.SystemTaskRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/9/6 16:29
 */
@Service("testJobService")
@Slf4j
public class TestJobService {

    private SystemTaskRegistrar systemTaskRegistrar;

    @Autowired(required = false)
    public void setSystemTaskRegistrar(SystemTaskRegistrar systemTaskRegistrar) {
        this.systemTaskRegistrar = systemTaskRegistrar;
    }

    @ScheduledTask
    public void execute(String args) {
        log.error("我是个数据库配置的Job, 我的作用是触发一次性任务 [{}] [{}]", args, DateUtil.formatLong(DateUtil.getNow()));
        OnceDetail onceDetail = new OnceDetail();
        onceDetail.setBeanName("onceJobService");
        onceDetail.setNid("onceJobService");
        onceDetail.setMethodName("execute");
        onceDetail.setArgs("一次性任务入参");
        onceDetail.setExecuteTime(DateUtil.addSeconds(DateUtil.getNow(),10));
        systemTaskRegistrar.addTask(onceDetail);
    }

    @Scheduled(cron = "0 0 0-5 * * ?")
    public void annotationTest(){
        log.error("我是个注解配置的Job {}" , DateUtil.formatLong(DateUtil.getNow()));
    }
}
