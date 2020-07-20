package com.eghm.configuration.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * 延迟定时执行器
 *
 * @author 二哥很猛
 * @date 2018/9/11 11:01
 */
@Slf4j
public class SystemTimer implements Timer, Function<TimerTaskEntry, Void> {

    /**
     * 线程池
     */
    private ExecutorService taskExecutor;

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

    /**
     * 最大执行业务的线程数
     */
    private static final int DEFAULT_MAX_THREAD = 50;

    /**
     * 最小执行业务的线程数
     */
    private static final int DEFAULT_MIN_THREAD = 5;

    /**
     * 默认队列容量
     */
    private static final int DEFAULT_QUEUE_CAPACITY = 1000;

    private DelayQueue<TimerTaskList> queue = new DelayQueue<>();

    private AtomicInteger taskCounter = new AtomicInteger(0);

    private AtomicInteger threadCounter = new AtomicInteger(1);

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
     *
     * @param tickMs    初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     */
    public SystemTimer(long tickMs, int wheelSize) {
        this(tickMs, wheelSize, DEFAULT_MIN_THREAD, DEFAULT_MAX_THREAD, "timer-", DEFAULT_QUEUE_CAPACITY);
    }

    /**
     * 全局时间轮定时器
     *
     * @param threadPrefix 线程池名称
     * @param tickMs      初始值 一格多少毫秒
     * @param wheelSize   初始值 一圈的格数
     */
    public SystemTimer(long tickMs, int wheelSize, int minThread, int maxThread, String threadPrefix, int capacity) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = Time.getHiresClockMs();
        this.timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, queue);
        this.taskExecutor = new ThreadPoolExecutor(minThread, maxThread, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(capacity), this.threadFactory(threadPrefix));
    }

    private ThreadFactory threadFactory(String prefix) {
        return r -> new Thread(r, prefix + threadCounter.getAndIncrement());
    }

    /**
     * 全局时间轮定时器
     *
     * @param tickMs      初始值 一格多少毫秒
     * @param wheelSize   初始值 一圈的格数
     * @param minThread   最小业务线程数
     * @param maxThread   最大业务线程数
     */
    public SystemTimer(long tickMs, int wheelSize, int minThread, int maxThread) {
        this(tickMs, wheelSize, minThread, maxThread, "timer-", DEFAULT_QUEUE_CAPACITY);
    }

    @Override
    public void add(AbstractTimerTask task) {
        readLock.lock();
        try {
            this.addTimerTaskEntry(new TimerTaskEntry(task, task.getDelayMs() + Time.getHiresClockMs()));
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 添加任务条目
     *
     * @param timerTaskEntry entry对象 封装了TimerTask对象
     */
    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        //过期或取消时,会返回false
        if (!timingWheel.add(timerTaskEntry) && !timerTaskEntry.cancelled()) {
            //过期时执行一次
            taskExecutor.submit(timerTaskEntry.getAbstractTimerTask());
        }
    }

    @Override
    public boolean advanceClock(long timeoutMs) {
        try {
            TimerTaskList bucket = queue.poll(timeoutMs, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                writeLock.lock();
                try {
                    while (bucket != null) {
                        timingWheel.advanceClock(bucket.getExpiration());
                        bucket.flush(this);
                        bucket = queue.poll();
                    }
                } finally {
                    writeLock.unlock();
                }
                return true;
            }
        } catch (InterruptedException e) {
            log.error("获取队列头元素异常", e);
            Thread.currentThread().interrupt();
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

    public ExecutorService getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(ExecutorService taskExecutor) {
        this.taskExecutor = taskExecutor;
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

}
