package com.eghm.configuration.timer;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 时间轮: 包含该时间轮上待执行的任务信息,以及父时间轮(父时间轮以该时间轮最大值为起始值开始的)
 *
 * @author 二哥很猛
 * @since 2018/9/11 11:05
 */
@NotThreadSafe
public class TimingWheel {

    /**
     * 每一格的时间
     */
    private final long scaleMs;

    /**
     * 一圈的格子数 每个格子都有一个 buckets
     */
    private final int wheelSize;

    /**
     * 一圈的总时间 = scaleMs * wheelSize
     */
    private final long interval;

    /**
     * 所有桶内任务总数
     */
    private final AtomicInteger taskCounter;

    /**
     * 任务队列
     */
    private final DelayQueue<TaskBucket> queue;

    /**
     * 当前桶的任务列表
     */
    private final TaskBucket[] buckets;

    /**
     * 开启任务时的时间
     */
    private long currentTime;

    /**
     * 父级桶
     */
    private TimingWheel overflowWheel;


    public TimingWheel(long scaleMs, int wheelSize, long startMs, AtomicInteger taskCounter, DelayQueue<TaskBucket> queue) {
        this.scaleMs = scaleMs;
        this.wheelSize = wheelSize;
        this.taskCounter = taskCounter;
        this.queue = queue;
        this.interval = scaleMs * wheelSize;
        this.currentTime = startMs - (startMs % scaleMs);
        this.buckets = new TaskBucket[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            this.buckets[i] = new TaskBucket(taskCounter);
        }
    }

    /**
     * 添加新任务到时间轮上
     *
     * @param entry entry
     * @return true:添加成功, false:添加失败,任务已过期或者已取消
     */
    public boolean add(Entry entry) {


        if (entry.cancelled()) {
            return false;
        }
        //任务取消
        long expireMs = entry.getExpireMs();
        //任务过期
        if (expireMs < currentTime + scaleMs) {
            return false;
        }
        if (expireMs < (currentTime + interval)) {
            // 该处定位桶的下标也不分顺序
            long virtualId = expireMs / scaleMs;
            TaskBucket bucket = buckets[(int) (virtualId % wheelSize)];
            bucket.add(entry);
            // 做取证操作,这样每个桶中所有元素的过期时间都相同
            if (bucket.setExpire(virtualId * scaleMs)) {
                return queue.offer(bucket);
            }
            return true;
        }

        // 父桶中添加
        if (overflowWheel == null) {
            addOverflowWheel();
        }
        return overflowWheel.add(entry);
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
            // 该处是为了让currentTime更接近真实时间, 取余后 currentTime能直接落在某个刻度中
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

    public AtomicInteger getTaskCounter() {
        return taskCounter;
    }
}
