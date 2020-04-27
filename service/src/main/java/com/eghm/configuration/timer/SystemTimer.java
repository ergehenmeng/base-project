package com.eghm.configuration.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * 延迟定时执行器
 * @author 二哥很猛
 * @date 2018/9/11 11:01
 */
public class SystemTimer implements Timer,Function<TimerTaskEntry,Void> {

    /**
     * 线程池
     */
    private ExecutorService taskExecutor;

    /**
     * 线程名称
     */
    private String executeName;

    /**
     * 一格占的毫秒值
     */
    private long tickMs;

    /**
     * 时间轮大小
     */
    private int wheelSize;

    /**
     * 计时开始时间
     */
    private long startMs;


    private DelayQueue<TimerTaskList> queue = new DelayQueue<>();

    private AtomicInteger taskCounter = new AtomicInteger(0);

    /**
     * 时间轮对象
     */
    private TimingWheel timingWheel;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    /**
     * 写锁
     */
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * 全局时间轮定时器
     * @param executeName 线程池名称
     * @param tickMs 初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     */
    public SystemTimer(String executeName,long tickMs,int wheelSize) {
        this.executeName = executeName;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = Time.getHiresClockMs();
        this.taskExecutor = Executors.newFixedThreadPool(100,r -> new Thread(r,executeName));
        this.timingWheel = new TimingWheel(tickMs,wheelSize,startMs,taskCounter,queue);
    }

    @Override
    public void add(TimerTask task) {
        readLock.lock();
        try {
            this.addTimerTaskEntry(new TimerTaskEntry(task,task.getDelayMs() + Time.getHiresClockMs()));
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 添加任务条目
     * @param timerTaskEntry entry对象 封装了TimerTask对象
     */
    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry){
        //过期或取消时,会返回false
        if (!timingWheel.add(timerTaskEntry)){
            //过期了,需要执行
            if(!timerTaskEntry.cancelled()){
                //过期时执行一次
                taskExecutor.submit(timerTaskEntry.getTimerTask());
            }
        }
    }

    @Override
    public boolean advanceClock(long timeoutMs) {
        try {
            TimerTaskList bucket = queue.poll(timeoutMs,TimeUnit.MILLISECONDS);
            if(bucket != null){
                writeLock.lock();
                try {
                    while (bucket != null){
                        timingWheel.advanceClock(bucket.getExpiration());
                        bucket.flush(this);
                        bucket = queue.poll();
                    }
                }finally {
                    writeLock.unlock();
                }
                return true;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int size() {
        return taskCounter.get();
    }

    @Override
    public void shutdown() {
        taskExecutor.shutdown();
    }

    @Override
    public Void apply(TimerTaskEntry timerTaskEntry) {
        addTimerTaskEntry(timerTaskEntry);
        return null;
    }

    public long getTickMs() {
        return tickMs;
    }

    public int getWheelSize() {
        return wheelSize;
    }

    public long getStartMs() {
        return startMs;
    }

    public String getExecuteName() {
        return executeName;
    }
}
