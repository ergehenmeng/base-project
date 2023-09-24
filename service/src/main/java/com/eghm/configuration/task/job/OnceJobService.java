package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.CronMark;
import com.eghm.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2020/1/7 19:57
 */
@Service("onceJobService")
@Slf4j
public class OnceJobService {

    @CronMark
    public void execute(String args) {
        log.error("我只是个执行一次的任务 [{}] [{}]", args, DateUtil.formatLong(DateUtil.getNow()));
    }
}
