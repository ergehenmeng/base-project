package com.eghm.configuration.timer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 延迟定时执行器
 *
 * @author 二哥很猛
 * @since 2018/9/11 11:01
 */
@Slf4j
public class SystemTimer {

    /**
     * 默认队列容量
     */
    private static final int DEFAULT_QUEUE_CAPACITY = 100000;

    /**
     * 最大执行业务的线程数
     */
    private static final int DEFAULT_MAX_THREAD = 50;

    /**
     * 最小执行业务的线程数
     */
    private static final int DEFAULT_CORE_THREAD = 5;

    /**
     * 最底层时间轮对象
     */
    private final TimingWheel rootWheel;

    /**
     * 该队列间接关联时间轮的所有任务,即:每个刻度的所有任务,同时也包含所有时间轮的任务
     */
    private final DelayQueue<TaskBucket> queue = new DelayQueue<>();

    private final AtomicInteger taskCounter = new AtomicInteger(0);

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);

    /**
     * 工作线程
     */
    private final ExecutorService executor;

    /**
     * 指针线程
     */
    private static final ScheduledExecutorService BOSS_EXECUTOR = Executors.newScheduledThreadPool(1);

    /**
     * 关闭处理
     */
    @Setter
    private ShutdownHandler shutdownHandler;

    /**
     * 全局时间轮定时器
     *
     * @param scaleMs   初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     */
    public SystemTimer(long scaleMs, int wheelSize) {
        this(scaleMs, wheelSize, new ThreadPoolExecutor(DEFAULT_CORE_THREAD, DEFAULT_MAX_THREAD, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(DEFAULT_QUEUE_CAPACITY), r -> new Thread(r, "时间轮线程-" + THREAD_COUNTER.getAndIncrement())));
    }

    /**
     * 全局时间轮定时器
     *
     * @param scaleMs   初始值 一格多少毫秒
     * @param wheelSize 初始值 一圈的格数
     */
    public SystemTimer(long scaleMs, int wheelSize, ExecutorService executor) {
        this.rootWheel = new TimingWheel(scaleMs, wheelSize, System.currentTimeMillis(), taskCounter, queue);
        this.executor = executor;
    }

    /**
     * 启动时间轮
     */
    public void start() {
        BOSS_EXECUTOR.schedule(this::advanceClock, 1, TimeUnit.SECONDS);
    }

    /**
     * 添加新任务
     *
     * @param task 任务
     */
    public void addTask(BaseTask task) {
        this.addEntry(new Entry(task, task.getDelayMs() + System.currentTimeMillis()));
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
            executor.submit(entry.getBaseTask());
        }
    }

    /**
     * 时间移动步长
     *
     */
    private void advanceClock() {
        try {
            TaskBucket bucket = queue.poll(200, TimeUnit.MILLISECONDS);
            while (bucket != null) {
                rootWheel.advanceClock(bucket.getExpire());
                bucket.flush(this::addEntry);
                bucket = queue.poll();
            }
        } catch (InterruptedException e) {
            log.error("获取队列头元素异常", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @return 当前任务总数
     */
    public int size() {
        return taskCounter.get();
    }

    /**
     * 关闭定时任务执行器
     */
    public void shutdown() {
        executor.shutdown();
        BOSS_EXECUTOR.shutdown();
        if (shutdownHandler != null) {
            shutdownHandler.shutdown(queue);
        }
    }

    @PreDestroy
    public void destroy() {
        shutdown();
    }

    @PostConstruct
    public void afterPropertiesSet() {
        start();
    }
}
