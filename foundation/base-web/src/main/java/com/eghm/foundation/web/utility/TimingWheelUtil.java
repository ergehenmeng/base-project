package com.eghm.foundation.web.utility;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * 时间轮
 */
@Slf4j
public class TimingWheelUtil {

    /**
     * 执行的具体任务接口
     */
    public interface TimerTask extends Runnable {
    }

    @Data
    static class TimerTaskEntry implements Comparable<TimerTaskEntry> {

        volatile TimerTaskList timedTaskList;

        TimerTaskEntry next;

        TimerTaskEntry prev;

        final TimerTask timerTask;

        final long expirationMs;

        TimerTaskEntry(TimerTask timerTask, long expirationMs) {
            this.timerTask = timerTask;
            this.expirationMs = expirationMs;
        }

        public boolean cancelled() {
            return timerTask == null;
        }

        public void remove() {
            TimerTaskList currentList = timedTaskList;
            while (currentList != null) {
                currentList.remove(this);
                currentList = timedTaskList;
            }
        }

        @Override
        public int compareTo(TimerTaskEntry o) {
            return Long.compare(this.expirationMs, o.expirationMs);
        }
    }

    /**
     * 时间轮的一个槽，内部是一个双向链表。
     * 实现了 Delayed 接口，以便放入 DelayQueue 中驱动时钟。
     */
    static class TimerTaskList implements Delayed {

        /**
         * 哨兵节点
         */
        private final TimerTaskEntry root = new TimerTaskEntry(null, -1);

        /**
         * 该槽的过期时间（决定何时从 DelayQueue 弹出）
         */
        private final AtomicLong expiration = new AtomicLong(-1L);

        public TimerTaskList() {
            root.next = root;
            root.prev = root;
        }

        /**
         * 设置过期时间，如果改变了意味着需要重新入队 DelayQueue
         */
        public boolean setExpiration(long expirationMs) {
            return expiration.getAndSet(expirationMs) != expirationMs;
        }

        public long getExpiration() {
            return expiration.get();
        }

        public void add(TimerTaskEntry entry) {
            boolean done = false;
            while (!done) {
                // 简单的链表插入同步，Kafka 使用了 synchronized (this)
                synchronized (this) {
                    if (entry.timedTaskList == null) {
                        entry.timedTaskList = this;
                        TimerTaskEntry tail = root.prev;
                        entry.next = root;
                        entry.prev = tail;
                        tail.next = entry;
                        root.prev = entry;
                        done = true;
                    }
                }
            }
        }

        public void remove(TimerTaskEntry entry) {
            synchronized (this) {
                if (entry.timedTaskList == this) {
                    entry.next.prev = entry.prev;
                    entry.prev.next = entry.next;
                    entry.next = null;
                    entry.prev = null;
                    entry.timedTaskList = null;
                }
            }
        }

        /**
         * 槽到期时，清空链表，将所有任务取出重新处理（执行或降级）
         */
        public synchronized void flush(Consumer<TimerTaskEntry> flushEntry) {
            TimerTaskEntry head = root.next;
            while (head != root) {
                remove(head);
                flushEntry.accept(head);
                head = root.next;
            }
            expiration.set(-1L);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(), 0), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(@Nonnull Delayed o) {
            if (o instanceof TimerTaskList v) {
                return Long.compare(this.getExpiration(), v.getExpiration());
            }
            return 0;
        }
    }

    static class TimingWheel {

        /**
         * 一个时间格跨度
         */
        private final long tickMs;

        /**
         * 时间轮格数
         */
        private final int wheelSize;

        /**
         * 时间轮总跨度 tickMs * wheelSize
         */
        private final long interval;

        private final TimerTaskList[] buckets;

        /**
         * 当前指针指向的时间（会被修剪为 tickMs 的整数倍）
         */
        private long currentTime;

        /**
         * 上层时间轮（懒加载）
         */
        private volatile TimingWheel overflowWheel;

        /**
         * 全局共用的 DelayQueue
         */
        private final DelayQueue<TimerTaskList> delayQueue;

        public TimingWheel(long tickMs, int wheelSize, long startMs, DelayQueue<TimerTaskList> delayQueue) {
            this.tickMs = tickMs;
            this.wheelSize = wheelSize;
            this.interval = tickMs * wheelSize;
            this.buckets = new TimerTaskList[wheelSize];
            this.currentTime = startMs - (startMs % tickMs);
            this.delayQueue = delayQueue;
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new TimerTaskList();
            }
        }

        private TimingWheel getOverflowWheel() {
            if (overflowWheel == null) {
                synchronized (this) {
                    if (overflowWheel == null) {
                        // 上层时间轮 tickMs 是当前层的 interval
                        overflowWheel = new TimingWheel(interval, wheelSize, currentTime, delayQueue);
                    }
                }
            }
            return overflowWheel;
        }

        /**
         * 添加任务到时间轮
         * @return true if added successfully to a bucket, false if cancelled or expired
         */
        public boolean add(TimerTaskEntry entry) {
            long expiration = entry.expirationMs;
            if (entry.cancelled()) {
                return false;
            } else if (expiration < currentTime + tickMs) {
                // 任务已过期（或在当前 tick 内）,返回 false 让上层立即提交执行
                return false;
            } else if (expiration < currentTime + interval) {
                // 任务在当前时间轮范围内
                long virtualId = expiration / tickMs;
                TimerTaskList bucket = buckets[(int) (virtualId % wheelSize)];
                bucket.add(entry);
                // 设置 Bucket 过期时间,如果这是新过期的 Bucket,放入 DelayQueue
                if (bucket.setExpiration(virtualId * tickMs)) {
                    delayQueue.offer(bucket);
                }
                return true;
            } else {
                // 超出范围，放入上层时间轮
                return getOverflowWheel().add(entry);
            }
        }

        /**
         * 推进时间指针
         */
        public void advanceClock(long timeMs) {
            if (timeMs >= currentTime + tickMs) {
                currentTime = timeMs - (timeMs % tickMs);
                // 如果有上层轮，也要推进
                if (overflowWheel != null) {
                    overflowWheel.advanceClock(currentTime);
                }
            }
        }
    }

    static class SystemTimer {

        private final TimingWheel timingWheel;

        private final DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();

        /**
         * 执行具体任务的线程池
         */
        private final ExecutorService taskExecutor;

        /**
         * 驱动时间轮的线程
         */
        private final ExecutorService bossExecutor;

        /**
         * 用读写锁保护时间轮的推进和添加
         */
        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public SystemTimer(ExecutorService taskExecutor) {
            this.taskExecutor = taskExecutor;
            this.bossExecutor = Executors.newSingleThreadExecutor(r -> new Thread(r, "SystemTimer-Boss"));
            // 底层时间轮：1ms 一格，20格，共20ms范围（测试用小一点方便观察层级升级，实际通常 tickMs=1, size=20）
            // 实际生产通常 tickMs=1ms, wheelSize=20
            this.timingWheel = new TimingWheel(1, 20, System.currentTimeMillis(), delayQueue);
            // 启动驱动线程
            this.bossExecutor.submit(this::run);
        }

        public void add(TimerTask task, long delayMs) {
            long expirationMs = System.currentTimeMillis() + delayMs;
            TimerTaskEntry entry = new TimerTaskEntry(task, expirationMs);
            addTimerTaskEntry(entry);
        }

        private void addTimerTaskEntry(TimerTaskEntry entry) {
            if (!timingWheel.add(entry) && !entry.cancelled()) {
                    taskExecutor.submit(entry.timerTask);
            }
        }

        /**
         * 重新插入（用于 Bucket 弹出后的降级操作）
         */
        private final Consumer<TimerTaskEntry> reinsert = this::addTimerTaskEntry;

        /**
         * 核心循环：从 DelayQueue 拉取到期的 Bucket
         */
        private void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimerTaskList bucket = delayQueue.poll(200, TimeUnit.MILLISECONDS);
                    if (bucket != null) {
                        readWriteLock.writeLock().lock();
                        try {
                            // 1. 推进时间轮的时间到 Bucket 的过期时间
                            while (bucket.getExpiration() > timingWheel.currentTime + timingWheel.tickMs) {
                                timingWheel.advanceClock(bucket.getExpiration());
                            }
                            // 2. 刷新 Bucket，将里面的任务取出，重新 add (触发降级或执行)
                            bucket.flush(reinsert);
                        } finally {
                            readWriteLock.writeLock().unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("SystemTimer error", e);
                }
            }
        }

        public void shutdown() {
            bossExecutor.shutdownNow();
            taskExecutor.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Kafka Hierarchical Timing Wheel Demo ===");
        // 用于执行实际业务逻辑的线程池
        ExecutorService workerPool = Executors.newFixedThreadPool(2);
        SystemTimer timer = new SystemTimer(workerPool);
        long start = System.currentTimeMillis();
        // 1. 添加短延时任务 (20ms内, Layer 1)
        timer.add(() -> printTask("Task A (1000ms)", start), 1000);
        // 2. 添加中延时任务 (超出 20ms, 需要 Layer 2: 20ms * 20 = 400ms)
        timer.add(() -> printTask("Task B (15000ms)", start), 15000);
        // 3. 添加长延时任务 (超出 400ms, 需要 Layer 3)
        timer.add(() -> printTask("Task C (50000ms)", start), 50000);
        // 4. 添加更长延时任务
        timer.add(() -> printTask("Task D (100000ms)", start), 100000);
        System.out.println("Tasks submitted. Waiting...");
        // 等待足够长的时间让任务执行
        Thread.sleep(1800000);
        System.out.println("=== Test Finished ===");
        timer.shutdown();
        workerPool.shutdown();
    }

    private static void printTask(String name, long startTime) {
        long now = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        System.out.printf("[%d ms] Executed: %s (Delta: %d ms)%n",
                now - startTime, name, (now - startTime));
        System.out.println(LocalDateTime.now());
    }
}