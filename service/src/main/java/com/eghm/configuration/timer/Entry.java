package com.eghm.configuration.timer;

/**
 * 存放TimerTask任务 双向链表结构 最后一个对象的下一个元素持有第一个元素引用 第一个元素的前一个对象持有最后一个元素的引用
 *
 * @author 二哥很猛
 * @date 2018/9/11 9:15
 */
public class Entry {

    /**
     * 当前entry的下一个对象
     */
    Entry next;
    /**
     * 当前entry的上一个对象
     */
    Entry prev;
    /**
     * 存放entry的列表,相互引用
     */
    private TaskBucket taskBucket;
    /**
     * 真实要执行的任务对象
     */
    private BaseTask baseTask;

    /**
     * 任务延迟执行时间 (2000 + Date.millisTime()) 表示:2000毫秒之后执行
     */
    private long expireMs;

    /**
     * 构造方法
     *
     * @param baseTask 定时任务
     * @param expireMs 到期执行时间
     */
    public Entry(BaseTask baseTask, long expireMs) {
        if (baseTask != null) {
            baseTask.setEntry(this);
        }
        this.baseTask = baseTask;
        this.expireMs = expireMs;
    }

    /**
     * 任务是否已经被删除(取消)
     *
     * @return true 已删除(取消) false 未取消
     */
    public boolean cancelled() {
        return baseTask.getEntry() != this;
    }

    /**
     * 移除当前对象
     */
    public void remove() {
        TaskBucket currentList = taskBucket;
        while (currentList != null) {
            currentList.remove(this);
            currentList = taskBucket;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 获取任务
     *
     * @return timerTask
     */
    public BaseTask getBaseTask() {
        return baseTask;
    }

    public long getExpireMs() {
        return expireMs;
    }

    public TaskBucket getTaskBucket() {
        return taskBucket;
    }

    public void setTaskBucket(TaskBucket taskBucket) {
        this.taskBucket = taskBucket;
    }
}
