package com.eghm.configuration.timer;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 延迟定时执行器
 *
 * @author 二哥很猛
 * @date 2018/9/11 11:01
 */
@Slf4j
public class SystemTimer {

    /**
     * 工作线程
     */
    private ExecutorService executor;

    /**
     * 指针线程
     */
    private ExecutorService bossExecutor;

    /**
     * 最底层时间轮对象
     */
    private TimingWheel rootWheel;

    /**
     * 关闭处理
     */
    private ShutdownHandler shutdownHandler;

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
     * 默认步长 200毫秒
     */
    private static final int DEFAULT_STEP = 200;

    /**
     * 是否开启任务
     */
    private volatile boolean started = false;

    /**
     * 该队列间接关联时间轮的所有任务,即:每个刻度的所有任务,同时也包含所有时间轮的任务
     */
    private DelayQueue<TaskBucket> queue = new DelayQueue<>();

    private AtomicInteger taskCounter = new AtomicInteger(0);

    private AtomicInteger threadCounter = new AtomicInteger(1);

    /**
     * 启动时间轮
     */
    public void start() {
        this.start(DEFAULT_STEP);
    }

    /**
     * 启动时间轮,以{step}的时间速度进行轮训
     * @param step 步长
     */
    public void start(long step) {
        if (executor == null) {
            executor = new ThreadPoolExecutor(DEFAULT_CORE_THREAD, DEFAULT_MAX_THREAD,
                    5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(DEFAULT_QUEUE_CAPACITY), r -> new Thread(r, "时间轮线程-" + threadCounter.getAndIncrement()));
        }
        this.initTimer(step);
    }

    /**
     * 初始化刻度线程
     * @param ms 毫秒
     */
    private void initTimer(long ms) {
        if (!started) {
            started = true;
            this.bossExecutor = Executors.newFixedThreadPool(1);
            bossExecutor.submit(() -> {
                while (started) {
                    this.advanceClock(ms);
                }
            });
        }
    }

    /**
     * 全局时间轮定时器
     *
     * @param scaleMs   初始值 一格多少毫秒
     * @param wheelSize  初始值 一圈的格数
     */
    public SystemTimer(long scaleMs, int wheelSize) {
        this.rootWheel = new TimingWheel(scaleMs, wheelSize, System.currentTimeMillis(), taskCounter, queue);
    }

    /**
     * 添加新任务
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
     * @param ms 毫秒
     */
    private void advanceClock(long ms) {
        try {
            TaskBucket bucket = queue.poll(ms, TimeUnit.MILLISECONDS);
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
        if (started) {
            started = false;
            executor.shutdown();
            bossExecutor.shutdown();
            if (shutdownHandler != null) {
                shutdownHandler.shutdown(queue);
            }
        }
    }

    public void setShutdownHandler(ShutdownHandler shutdownHandler) {
        this.shutdownHandler = shutdownHandler;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
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
