package com.eghm.configuration.timer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @date 2018/9/11 11:56
 */
@Slf4j
public class TimerTaskOperation extends AbstractTimerTask {

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行
     */
    public TimerTaskOperation(long delayMs) {
        super(delayMs);
    }

    @Override
    public void execute() {
        log.info("任务执行:" + Thread.currentThread() + " : " + System.currentTimeMillis());
    }
}
