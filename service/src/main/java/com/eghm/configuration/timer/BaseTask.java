package com.eghm.configuration.timer;

import java.time.Duration;

/**
 * 任务接口
 *
 * @author 二哥很猛
 * @date 2018/9/11 9:19
 */
public abstract class BaseTask implements Runnable {

    /**
     * 存放该TimerTask的entry对象,相互引用
     */
    private Entry entry;

    /**
     * 延迟多长时间执行 毫秒值
     */
    private long delayMs;

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行,例如 4000 表示4秒后执行
     */
    public BaseTask(long delayMs) {
        this.delayMs = delayMs;
    }

    public BaseTask(Duration duration) {
        this(duration.toMillis());
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
            if (entry != null) {
                entry.remove();
            }
            entry = null;
        }
    }

    /**
     * 如果存在,先删除旧entry,再赋值
     *
     * @param entry 新entry
     */
    public synchronized void setEntry(Entry entry) {
        if (this.entry != null && this.entry != entry) {
            this.entry.remove();
        }
        this.entry = entry;
    }

    public synchronized Entry getEntry() {
        return entry;
    }

    public long getDelayMs() {
        return delayMs;
    }

}
