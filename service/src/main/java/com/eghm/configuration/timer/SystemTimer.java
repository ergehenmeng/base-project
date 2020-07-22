package com.eghm.configuration.timer;

import com.eghm.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 延迟定时执行器
 *
 * @author 二哥很猛
 * @date 2018/9/11 11:01
 */
@Slf4j
public class SystemTimer implements Timer, Consumer<Entry> {

    /**
     * 线程池
     */
    private ExecutorService taskExecutor;

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

    /**
     * 该队列间接关联时间轮的所有任务,即:每个刻度的所有任务,同时也包含所有时间轮的任务
     */
    private DelayQueue<TaskBucket> queue = new DelayQueue<>();

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
        this.rootWheel = new TimingWheel(scaleMs, wheelSize, DateUtil.millisTime(), taskCounter, queue);
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
    public void addTask(BaseTask task) {
        readLock.lock();
        try {
            this.addEntry(new Entry(task, task.getDelayMs() + DateUtil.millisTime()));
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 添加任务条目
     *
     * @param entry entry对象 封装了TimerTask对象
     */
    private void addEntry(Entry entry) {
        //过期或取消时,会返回false
        if (!rootWheel.add(entry) && !entry.cancelled()) {
            //过期时执行一次
            taskExecutor.submit(entry.getBaseTask());
        }
    }

    @Override
    public void advanceClock(long seconds) {
        try {
            TaskBucket bucket = queue.poll(seconds, TimeUnit.SECONDS);
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
    public void accept(Entry entry) {
        // 将任务重新添加到时间轮中(任务可能由父时间轮转为子时间轮)
        addEntry(entry);
    }


}
