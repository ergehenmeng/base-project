package com.eghm.configuration.timer;

import java.util.concurrent.DelayQueue;

/**
 * 时间轮关闭处理
 *
 * @author 殿小二
 * @date 2020/7/22
 */
public interface ShutdownHandler {

    /**
     * 关闭任务处理
     *
     * @param queue 尚未执行的任务
     */
    void shutdown(DelayQueue<TaskBucket> queue);
}
