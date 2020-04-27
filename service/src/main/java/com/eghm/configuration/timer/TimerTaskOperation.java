package com.eghm.configuration.timer;

/**
 * @author 二哥很猛
 * @date 2018/9/11 11:56
 */
public class TimerTaskOperation extends AbstractTimerTask {

    /**
     * 构造方法
     *
     * @param delayMs 延迟多长时间执行
     */
    public TimerTaskOperation(long delayMs) {
        super(delayMs);
    }

    @Override
    public void execute() {
        System.out.println("任务执行:" + Thread.currentThread() + " : " + System.currentTimeMillis());
    }
}
