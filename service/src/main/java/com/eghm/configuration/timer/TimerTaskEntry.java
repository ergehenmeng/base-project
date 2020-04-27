package com.eghm.configuration.timer;

import javax.annotation.Nonnull;

/**
 * 存放TimerTask任务 双向链表结构 最后一个对象的下一个元素持有第一个元素引用 第一个元素的前一个对象持有最后一个元素的引用
 * @author 二哥很猛
 * @date 2018/9/11 9:15
 */
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    /**
     * 存放entry的列表,相互引用
     */
    private volatile TimerTaskList timerTaskList;

    /**
     * 当前entry的下一个对象
     */
    TimerTaskEntry next;

    /**
     * 当前entry的上一个对象
     */
    TimerTaskEntry prev;

    /**
     * 真实要执行的任务对象
     */
    private TimerTask timerTask;

    /**
     * 任务延迟执行时间 2000 + Time.getHiresClockMs() 表示:2000毫秒之后执行
     */
    private long expirationMs;

    /**
     * 构造方法
     * @param timerTask 定时任务
     * @param expirationMs  到期执行时间
     */
    public TimerTaskEntry(TimerTask timerTask,long expirationMs){
        if(timerTask != null){
            timerTask.setTimerTaskEntry(this);
        }
        this.timerTask = timerTask;
        this.expirationMs = expirationMs;
    }

    /**
     * 任务是否已经被删除(取消)
     * @return true 已删除(取消) false 未取消
     */
    public boolean cancelled(){
        return timerTask.getTimerTaskEntry() != this;
    }

    /**
     * 移除当前对象
     */
    public void remove(){
        TimerTaskList currentList = timerTaskList;
        while (currentList != null){
            currentList.remove(this);
            currentList = timerTaskList;
        }
    }

    @Override
    public int compareTo(@Nonnull TimerTaskEntry o) {
        Long expirationMs1 = this.getExpirationMs();
        Long expirationMs2 = o.getExpirationMs();
        if (expirationMs1 < expirationMs2) {
            return -1;
        }
        if (expirationMs1 > expirationMs2) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取任务
     * @return timerTask
     */
    public TimerTask getTimerTask() {
        return timerTask;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public TimerTaskList getTimerTaskList() {
        return timerTaskList;
    }

    public void setTimerTaskList(TimerTaskList timerTaskList) {
        this.timerTaskList = timerTaskList;
    }
}
