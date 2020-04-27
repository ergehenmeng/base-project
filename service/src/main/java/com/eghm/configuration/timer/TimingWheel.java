package com.eghm.configuration.timer;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 存放任务
 * @author 二哥很猛
 * @date 2018/9/11 11:05
 */
@NotThreadSafe
public class TimingWheel {

    /**
     * 每一格的时间
     */
    private long tickMs;

    /**
     * 一圈的格子数 每个格子都有一个 buckets
     */
    private int wheelSize;

    /**
     * 一圈的总时间 = tickMs * wheelSize
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
    private DelayQueue<TimerTaskList> queue;

    /**
     * 开启任务时的时间
     */
    private long currentTime;

    /**
     * 父级桶
     */
    private volatile TimingWheel overflowWheel;

    /**
     * 当前桶的任务列表
     */
    private TimerTaskList[] buckets;


    public TimingWheel(long tickMs, int wheelSize,long startMs,AtomicInteger taskCounter,DelayQueue<TimerTaskList> queue){
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;
        this.interval = tickMs * wheelSize;
        this.currentTime = startMs - (startMs % tickMs);

        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < wheelSize; i++){
            this.buckets[i] = new TimerTaskList(taskCounter);
        }
    }

    /**
     * 添加新任务到时间轮上
     * @param timerTaskEntry entry
     * @return true:添加成功, false:添加失败,任务已过期或者已取消
     */
    public boolean add(TimerTaskEntry timerTaskEntry){
        long expiration = timerTaskEntry.getExpirationMs();
        if(timerTaskEntry.cancelled()){
            //任务取消
            return false;
        }else if(expiration < currentTime + tickMs){
            //任务过期
            return false;
        }else if(expiration < currentTime + interval){
            //当前桶内查找
            long virtualId = expiration / tickMs;
            TimerTaskList bucket = buckets[(int)(virtualId % wheelSize)];
            bucket.add(timerTaskEntry);
            if(bucket.setExpiration(virtualId * tickMs)){
                queue.offer(bucket);
            }
            return true;
        }else{
            //父级桶内查询
            if(overflowWheel == null){
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }

    /**
     * 添加父级桶
     */
    private void addOverflowWheel() {
        synchronized (this){
            if(overflowWheel == null){
                overflowWheel = new TimingWheel(interval,wheelSize,currentTime,taskCounter,queue);
            }
        }
    }

    /**
     * 指针向前移动(更新当前时间)
     * @param timeMs 走多少时间
     */
    public void advanceClock(long timeMs){
        if(timeMs >= currentTime + tickMs){
            currentTime = timeMs - (timeMs % tickMs);
            if(overflowWheel != null){
                overflowWheel.advanceClock(currentTime);
            }
        }
    }

    public long getTickMs() {
        return tickMs;
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
