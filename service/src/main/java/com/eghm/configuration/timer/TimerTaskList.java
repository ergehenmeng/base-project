package com.eghm.configuration.timer;

import javax.annotation.Nonnull;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * 存放任务的列表
 * @author 二哥很猛
 * @date 2018/9/11 9:14
 */
public class TimerTaskList implements Delayed {

    /**
     * 任务数统计,所有桶公用
     */
    private AtomicInteger taskCounter;

    /**
     * 根任务,默认为空,不进行任务处理
     */
    private TimerTaskEntry root;

    /**
     * 该TimerTaskList的过期时间
     */
    private AtomicLong expiration;


    public TimerTaskList(AtomicInteger taskCounter){
        this.taskCounter = taskCounter;
        this.root = new TimerTaskEntry(null,-1L);
        this.root.prev = root;
        this.root.next = root;
        this.expiration = new AtomicLong(-1L);
    }

    @Override
    public long getDelay(@Nonnull TimeUnit unit) {
        long hiresClockMs = Time.getHiresClockMs();
        long expire = getExpiration() - hiresClockMs;
        return unit.convert(Long.max(expire,0),TimeUnit.MILLISECONDS);
    }


    @Override
    public int compareTo(@Nonnull Delayed o) {
        TimerTaskList other;
        if(o instanceof TimerTaskList){
            other = (TimerTaskList)o;
        }else{
            throw new ClassCastException("TimerTaskList类型转换错误");
        }

        if(getExpiration() < other.getExpiration()){
            return -1;
        }else if(getExpiration() > other.getExpiration()){
            return 1;
        }
        return 0;
    }

    /**
     * 设置 桶的到期时间
     * @param expirationMs 到期时间 毫秒值
     * @return true 到期时间被改变
     */
    public boolean setExpiration(Long expirationMs){
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    /**
     * 获取桶的到期时间
     * @return 到期时间
     */
    public Long getExpiration(){
        return expiration.get();
    }

    /**
     * 将提供的函数应用于此列表的每个任务中
     * @param function 需要执行的函数
     */
    public synchronized void foreach(Function<TimerTask,Void> function){
        TimerTaskEntry entry = root.next;
        while (entry != root){
            TimerTaskEntry nextEntry = entry.next;
            if(!entry.cancelled()){
                function.apply(entry.getTimerTask());
            }
            entry = nextEntry;
        }
    }

    /**
     * 添加任务
     * @param timerTaskEntry 任务entry
     */
    public void add(TimerTaskEntry timerTaskEntry){
        boolean done = false;
        while (!done){
            //为防止任务已经在其他列表中,先移除其他列表的该任务
            //为防止死锁,在外面进行操作
            timerTaskEntry.remove();
            synchronized (this){
                if(timerTaskEntry.getTimerTaskList() == null){
                    //将该任务添加到列表的最后 root
                    TimerTaskEntry tail = root.prev;
                    timerTaskEntry.next = root;
                    timerTaskEntry.prev = tail;
                    timerTaskEntry.setTimerTaskList(this);
                    tail.next = timerTaskEntry;
                    root.prev = timerTaskEntry;
                    taskCounter.incrementAndGet();
                    done = true;
                }
            }
        }
    }

    /**
     * 移除一个任务
     * @param timerTaskEntry 任务
     */
    public void remove(TimerTaskEntry timerTaskEntry){
        synchronized (this){
            if(timerTaskEntry.getTimerTaskList() == this){
                timerTaskEntry.next.prev = timerTaskEntry.prev;
                timerTaskEntry.prev.next = timerTaskEntry.next;
                timerTaskEntry.next = null;
                timerTaskEntry.prev = null;
                timerTaskEntry.setTimerTaskList(null);
                taskCounter.decrementAndGet();
            }
        }
    }

    /**
     * 删除所有任务,并执行一次删除的任务
     * @param function 执行函数
     */
    public void flush(Function<TimerTaskEntry,Void> function){
        synchronized (this){
            TimerTaskEntry head = root.next;
            while (head != root){
                remove(head);
                function.apply(head);
                head = root.next;
            }
            expiration.set(-1L);
        }
    }
}
