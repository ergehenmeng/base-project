package com.eghm.configuration.timer;

/**
 * 定时器接口
 * @author 二哥很猛
 * @date 2018/9/11 10:56
 */
public interface Timer  {

    /**
     * 添加任务
     * @param task 任务
     */
    void add(AbstractTimerTask task);


    /**
     * 指针一次移动的时间(一次移动多少毫秒)
     * @param timeoutMs 过期毫秒值
     * @return 是否执行了任务
     */
    boolean advanceClock(long timeoutMs);

    /**
     * 等待执行的任务数
     * @return 个数
     */
    int size();


    /**
     * 关闭计时服务,保留未执行的任务
     */
    void shutdown();

}

