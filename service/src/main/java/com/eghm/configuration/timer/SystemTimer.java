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
public class SystemTimer implements Timer, Function<TaskEntry, Void> {

    /**
     * 线程池
     */
    private ExecutorService taskExecutor;

    /**
     * 计时开始时间
     */
    private long startTime;

    /**
     * 最底层时间轮对象
     */
    private TimingWheel rootWheel;

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
    private static final int DEFAULT_QUEUE_CAPACITY = 100000;

    private DelayQueue<TaskQueue> queue = new DelayQueue<>();

    private AtomicInteger taskCounter = new AtomicInteger(0);

    private AtomicInteger threadCounter = new AtomicInteger(1);

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
     * @param scaleMs   初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     */
    public SystemTimer(long scaleMs, int wheelSize) {
        this(scaleMs, wheelSize, DEFAULT_MIN_THREAD, DEFAULT_MAX_THREAD, DEFAULT_QUEUE_CAPACITY);
    }

    /**
     * 全局时间轮定时器
     *
     * @param scaleMs   初始值 一格多少毫秒
     * @param wheelSize  初始值 一圈的格数
     * @param minThread 线程池最小数
     * @param maxThread 线程池最大数
     * @param capacity 线程池任务最大容量
     */
    public SystemTimer(long scaleMs, int wheelSize, int minThread, int maxThread, int capacity) {
        this.startTime = System.currentTimeMillis();
        this.rootWheel = new TimingWheel(scaleMs, wheelSize, startTime, taskCounter, queue);
        setTaskExecutor(new ThreadPoolExecutor(minThread, maxThread, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(capacity), this.threadFactory()));
    }


    private ThreadFactory threadFactory() {
        return r -> new Thread(r, "时间轮-" + threadCounter.getAndIncrement());
    }

    /**
     * 全局时间轮定时器
     *
     * @param tickMs    初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     * @param minThread 最小业务线程数
     * @param maxThread 最大业务线程数
     */
    public SystemTimer(long tickMs, int wheelSize, int minThread, int maxThread) {
        this(tickMs, wheelSize, minThread, maxThread, DEFAULT_QUEUE_CAPACITY);
    }

    @Override
    public void addTask(AbstractTask task) {
        readLock.lock();
        try {
            this.addTimerTaskEntry(new TaskEntry(task, task.getDelayMs() + System.currentTimeMillis()));
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 添加任务条目
     *
     * @param taskEntry entry对象 封装了TimerTask对象
     */
    private void addTimerTaskEntry(TaskEntry taskEntry) {
        //过期或取消时,会返回false
        if (!rootWheel.add(taskEntry) && !taskEntry.cancelled()) {
            //过期时执行一次
            taskExecutor.submit(taskEntry.getAbstractTask());
        }
    }

    @Override
    public void advanceClock(long seconds) {
        try {
            TaskQueue bucket = queue.poll(seconds, TimeUnit.SECONDS);
            if (bucket != null) {
                writeLock.lock();
                try {
                    while (bucket != null) {
                        rootWheel.advanceClock(bucket.getExpiration());
                        bucket.flush(this);
                        bucket = queue.poll();
                    }
                } finally {
                    writeLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("获取队列头元素异常", e);
            Thread.currentThread().interrupt();
        }
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
    public Void apply(TaskEntry taskEntry) {
        addTimerTaskEntry(taskEntry);
        return null;
    }

    public long getStartTime() {
        return startTime;
    }

}
