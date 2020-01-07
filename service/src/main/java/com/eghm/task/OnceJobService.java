package com.eghm.task;

import com.eghm.common.utils.DateUtil;
import com.eghm.configuration.job.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2020/1/7 19:57
 */
@Service("onceJobService")
@Slf4j
public class OnceJobService implements Task {

    @Override
    public void execute() {
        log.error("我只是个执行一次的任务 {}", DateUtil.formatLong(DateUtil.getNow()));
    }
}
