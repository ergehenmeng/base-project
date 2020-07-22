package com.eghm.configuration.timer;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 时间轮: 包含该时间轮上待执行的任务信息,以及父时间轮(父时间轮以该时间轮最大值为起始值开始的)
 *
 * @author 二哥很猛
 * @date 2018/9/11 11:05
 */
@NotThreadSafe
public class TimingWheel {

    /**
     * 每一格的时间
     */
    private long scaleMs;

    /**
     * 一圈的格子数 每个格子都有一个 buckets
     */
    private int wheelSize;

    /**
     * 一圈的总时间 = scaleMs * wheelSize
     */
    private long interval;

    /**
     * 开始时间
     */
    private long startMs;

    /**
     * 所有桶内任务总数
     */
    private AtomicInteger taskCounter;

    /**
     * 任务队列
     */
    private DelayQueue<TaskQueue> queue;

    /**
     * 开启任务时的时间
     */
    private long currentTime;

    /**
     * 父级桶
     */
    private TimingWheel overflowWheel;

    /**
     * 当前桶的任务列表
     */
    private TaskQueue[] buckets;


    public TimingWheel(long scaleMs, int wheelSize, long startMs, AtomicInteger taskCounter, DelayQueue<TaskQueue> queue) {
        this.scaleMs = scaleMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;
        this.interval = scaleMs * wheelSize;
        this.currentTime = startMs - (startMs % scaleMs);
        this.buckets = new TaskQueue[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            this.buckets[i] = new TaskQueue(taskCounter);
        }
    }

    /**
     * 添加新任务到时间轮上
     *
     * @param taskEntry entry
     * @return true:添加成功, false:添加失败,任务已过期或者已取消
     */
    public boolean add(TaskEntry taskEntry) {
        long expiration = taskEntry.getExpirationMs();
        if (taskEntry.cancelled()) {
            //任务取消
            return false;
        } else if (expiration < currentTime + scaleMs) {
            //任务过期
            return false;
        } else if (expiration < currentTime + interval) {
            //当前桶内查找
            long virtualId = expiration / scaleMs;
            TaskQueue bucket = buckets[(int) (virtualId % wheelSize)];
            bucket.add(taskEntry);
            if (bucket.setExpiration(virtualId * scaleMs)) {
                return queue.offer(bucket);
            }
            return false;
        } else {
            //父级桶内查询
            if (overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(taskEntry);
        }
    }

    /**
     * 添加父级桶
     */
    private void addOverflowWheel() {
        synchronized (this) {
            if (overflowWheel == null) {
                overflowWheel = new TimingWheel(interval, wheelSize, currentTime, taskCounter, queue);
            }
        }
    }

    /**
     * 指针向前移动(更新当前时间)
     *
     * @param timeMs 走多少时间
     */
    public void advanceClock(long timeMs) {
        if (timeMs >= currentTime + scaleMs) {
            currentTime = timeMs - (timeMs % scaleMs);
            if (overflowWheel != null) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }

    public long getScaleMs() {
        return scaleMs;
    }

    public int getWheelSize() {
        return wheelSize;
    }

    public long getInterval() {
        return interval;
    }

    public long getStartMs() {
        return startMs;
    }

    public AtomicInteger getTaskCounter() {
        return taskCounter;
    }
}
