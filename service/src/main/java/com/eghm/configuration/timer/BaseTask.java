package com.eghm.configuration.timer;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务接口
 *
 * @author 二哥很猛
 * @since 2018/9/11 9:19
 */
@Slf4j
public abstract class BaseTask implements Runnable {

    /**
     * 延迟多长时间执行 毫秒值
     */
    private final long delayMs;

    /**
     * 存放该TimerTask的entry对象,相互引用
     */
    private Entry entry;

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行,例如 4000 表示4秒后执行
     */
    protected BaseTask(long delayMs) {
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (RuntimeException e) {
            this.exception(e);
        }
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

    public synchronized Entry getEntry() {
        return entry;
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

    public long getDelayMs() {
        return delayMs;
    }

    protected void exception(RuntimeException e) {
        log.error("任务执行异常", e);
    }

}
