package com.eghm.configuration.timer.job;

import com.eghm.utils.DateUtil;
import com.eghm.configuration.timer.BaseTask;
import com.eghm.configuration.timer.SystemTimer;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2018/9/11 11:56
 */
@Slf4j
public class TaskOperation extends BaseTask {

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行
     */
    public TaskOperation(long delayMs) {
        super(delayMs);
    }

    @Override
    public void execute() {
        log.info("任务执行:" + Thread.currentThread().getName() + " " + DateUtil.formatLong(new Date()));
    }

    public static void main(String[] args) {
        SystemTimer timer = new SystemTimer(100, 20);
        timer.start();
        timer.addTask(new TaskOperation(6000));
        timer.addTask(new TaskOperation(12000));
    }
}
