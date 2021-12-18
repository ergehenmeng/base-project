package com.eghm.configuration.timer;

import javax.annotation.Nonnull;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 存放任务的列表
 *
 * @author 二哥很猛
 * @date 2018/9/11 9:14
 */
public class TaskBucket implements Delayed {

    /**
     * 任务数统计,所有桶公用
     */
    private final AtomicInteger taskCounter;

    /**
     * 根任务,默认为空,不进行任务处理
     */
    private final Entry root;

    /**
     * 该TaskBucket的过期时间,内部所有任务的过期时间是一样的
     */
    private final AtomicLong expiration;

    /**
     * 当前桶中的任务数
     */
    private final AtomicInteger taskSize;

    public TaskBucket(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.root = new Entry(null, -1L);
        this.root.prev = root;
        this.root.next = root;
        this.expiration = new AtomicLong(-1L);
        this.taskSize = new AtomicInteger();
    }

    @Override
    public long getDelay(@Nonnull TimeUnit unit) {
        return Long.max(unit.convert(getExpire() - System.currentTimeMillis(), TimeUnit.MILLISECONDS), 0);
    }


    @Override
    public int compareTo(@Nonnull Delayed o) {
        if (!(o instanceof TaskBucket)) {
            throw new ClassCastException("TimerTaskList类型转换错误");
        }
        return Long.compare(this.getExpire(), ((TaskBucket) o).getExpire());
    }

    /**
     * 设置 桶的到期时间
     *
     * @param expireMs 到期时间 毫秒值
     * @return true 到期时间被改变
     */
    public boolean setExpire(Long expireMs) {
        return expiration.getAndSet(expireMs) != expireMs;
    }

    /**
     * 获取桶的到期时间
     *
     * @return 到期时间
     */
    public Long getExpire() {
        return expiration.get();
    }

    /**
     * 将提供的函数应用于此列表的每个任务中
     *
     * @param consumer 需要执行的函数
     */
    public synchronized void foreach(Consumer<BaseTask> consumer) {
        Entry entry = root.next;
        while (entry != root) {
            Entry nextEntry = entry.next;
            if (!entry.cancelled()) {
                consumer.accept(entry.getBaseTask());
            }
            entry = nextEntry;
        }
    }

    /**
     * 添加任务
     *
     * @param entry 任务entry
     */
    public void add(Entry entry) {
        boolean done = false;
        while (!done) {
            //为防止任务已经在其他列表中,先移除其他列表的该任务
            //为防止死锁,在外面进行操作
            entry.remove();
            synchronized (this) {
                if (entry.getTaskBucket() == null) {
                    //将该任务添加到列表的最后 root
                    Entry tail = root.prev;
                    entry.next = root;
                    entry.prev = tail;
                    entry.setTaskBucket(this);
                    tail.next = entry;
                    root.prev = entry;
                    taskCounter.incrementAndGet();
                    taskSize.incrementAndGet();
                    done = true;
                }
            }
        }
    }

    /**
     * 移除一个任务
     *
     * @param entry 任务
     */
    public void remove(Entry entry) {
        synchronized (this) {
            if (entry.getTaskBucket() == this) {
                entry.next.prev = entry.prev;
                entry.prev.next = entry.next;
                entry.next = null;
                entry.prev = null;
                entry.setTaskBucket(null);
                taskCounter.decrementAndGet();
                taskSize.decrementAndGet();
            }
        }
    }

    /**
     * 删除所有任务,并执行一次删除的任务
     *
     * @param consumer 执行函数
     */
    public void flush(Consumer<Entry> consumer) {
        synchronized (this) {
            Entry head = root.next;
            while (head != root) {
                remove(head);
                consumer.accept(head);
                head = root.next;
            }
            expiration.set(-1L);
        }
    }

    public int size() {
        return taskSize.get();
    }
}
