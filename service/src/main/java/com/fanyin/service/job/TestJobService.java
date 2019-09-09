package com.fanyin.service.job;

import com.fanyin.common.utils.DateUtil;
import com.fanyin.configuration.job.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/9/6 16:29
 */
@Service("testJobService")
@Slf4j
public class TestJobService implements Task {

    @Override
    public void execute() {
        log.error("我是个数据库配置的Job {}", DateUtil.formatLong(DateUtil.getNow()));
    }

    @Scheduled(cron = "0 0 0-5 * * ?")
    public void annotationTest(){
        log.error("我是个注解配置的Job {}" , DateUtil.formatLong(DateUtil.getNow()));
    }
}
