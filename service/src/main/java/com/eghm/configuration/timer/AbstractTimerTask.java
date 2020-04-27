package com.eghm.configuration.timer;

/**
 * 任务接口
 * @author 二哥很猛
 * @date 2018/9/11 9:19
 */
public abstract class AbstractTimerTask implements Runnable {

    /**
     * 存放该TimerTask的entry对象,相互引用
     */
    private TimerTaskEntry timerTaskEntry;

    /**
     * 延迟多长时间执行 毫秒值
     */
    private long delayMs;

    /**
     * 构造方法
     * @param delayMs 延迟多长时间执行
     */
    public AbstractTimerTask(long delayMs){
        this.delayMs = delayMs;
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
    public void cancel(){
        synchronized (this){
            if(timerTaskEntry != null){
                timerTaskEntry.remove();
            }
            timerTaskEntry = null;
        }
    }

    /**
     * 如果存在,先删除旧entry,再赋值
     * @param entry 新entry
     */
    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this){
            if(this.timerTaskEntry != null && this.timerTaskEntry != entry){
                this.timerTaskEntry.remove();
            }
            this.timerTaskEntry = entry;
        }
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }
}
