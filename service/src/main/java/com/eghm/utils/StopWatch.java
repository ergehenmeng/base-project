package com.eghm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 殿小二
 * @since 2021/1/20
 */
@Getter
@Setter
public class StopWatch {

    /**
     * 任务耗时记录项
     */
    private final List<TimeClock> taskList = new LinkedList<>();

    /**
     * 任务首次开始执行时间
     */
    private long startTime;

    /**
     * 最新的任务名称
     */
    private String taskName;

    /**
     * 任务耗时是否开始
     */
    private boolean started;

    /**
     * 所有任务总耗时
     */
    private long totalElapsedTime;

    private StopWatch(String taskName) {
        this.taskName = taskName;
        this.startTime = System.nanoTime();
        this.started = true;
    }

    public static StopWatch getStarted(String taskName) {
        return new StopWatch(taskName);
    }

    public void start(String taskName) {
        long nowTime = System.nanoTime();
        if (started) {
            this.createTaskClock(nowTime);
        }
        this.startTime = nowTime;
        this.taskName = taskName;
    }

    /**
     * 打印总耗时时间
     * 注意: 调用该方法自动会停止计时,如需继续请调用继续调用start方法
     *
     * @return 耗时格式化打印
     */
    public String prettyPrint() {
        if (!started) {
            return "任务未开启";
        }
        started = false;
        this.createTaskClock(System.nanoTime());
        StringBuilder builder = new StringBuilder("\nStopWatch 总耗时: ").append(TimeUnit.NANOSECONDS.toMillis(totalElapsedTime)).append(" ms");
        builder.append("\n");
        for (TimeClock clock : taskList) {
            builder.append("[").append(clock.getTaskName()).append("] 耗时(ms): ").append(TimeUnit.NANOSECONDS.toMillis(clock.getElapsedTime())).append("\n");
        }
        return builder.toString();
    }

    /**
     * 获取总耗时时间
     * 注意: 调用该方法自动会停止计时,如需继续请调用继续调用start方法
     *
     * @return 耗时时间 单位: ms
     */
    public long getElapsedTime() {
        if (started) {
            started = false;
            this.createTaskClock(System.nanoTime());
        }
        return TimeUnit.NANOSECONDS.toMillis(totalElapsedTime);
    }

    private void createTaskClock(long nowTime) {
        long nowElapsed = nowTime - startTime;
        this.totalElapsedTime += nowElapsed;
        taskList.add(new TimeClock(this.taskName, nowElapsed));
    }

    @Data
    @AllArgsConstructor
    private static final class TimeClock {

        private final String taskName;

        private final long elapsedTime;

    }
}
