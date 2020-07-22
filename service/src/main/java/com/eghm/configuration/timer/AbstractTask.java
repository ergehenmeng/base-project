package com.eghm.configuration.timer;

import java.time.Duration;

/**
 * 任务接口
 *
 * @author 二哥很猛
 * @date 2018/9/11 9:19
 */
public abstract class AbstractTask implements Runnable {

    /**
     * 存放该TimerTask的entry对象,相互引用
     */
    private TaskEntry taskEntry;

    /**
     * 延迟多长时间执行 毫秒值
     */
    private long delayMs;

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行
     */
    public AbstractTask(long delayMs) {
        this.delayMs = delayMs;
    }

    public AbstractTask(Duration duration) {
        this.delayMs = duration.toMillis();
    }

    @Override
    public void run() {
        execute();
    }

    /**
     * 执行业务信息
     */
    public abstract void execute();

    /**
     * 删除任务(取消任务执行)
     */
    public void cancel() {
        synchronized (this) {
            if (taskEntry != null) {
                taskEntry.remove();
            }
            taskEntry = null;
        }
    }

    /**
     * 如果存在,先删除旧entry,再赋值
     *
     * @param entry 新entry
     */
    public synchronized void setTaskEntry(TaskEntry entry) {
        if (this.taskEntry != null && this.taskEntry != entry) {
            this.taskEntry.remove();
        }
        this.taskEntry = entry;
    }

    public synchronized TaskEntry getTaskEntry() {
        return taskEntry;
    }

    public long getDelayMs() {
        return delayMs;
    }

}
