package com.fanyin.queue;

import com.fanyin.constants.TaskConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 二哥很猛
 * @date 2018/11/16 17:25
 */
@Slf4j
public class TaskHandler {

    private static class ExecutorHolder {

        /**
         * 操作日志 多线程处理
         */
        private static final ThreadPoolExecutor OPERATE_LOG = new ThreadPoolExecutor(5,10,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.OPERATE_LOG_THREAD),((r, executor) -> {
                    log.warn("操作日志的线程池已满,data:[{}]",((AbstractTask)r).getData());
        }));

        /**
         * 登陆日志 多线程处理
         */
        private static final ThreadPoolExecutor LOGIN_LOG = new ThreadPoolExecutor(1,2,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.LOGIN_LOG_THREAD),(r, executor) -> {
                    log.warn("登陆日志的线程池已满,data:[{}]",((AbstractTask)r).getData());
        });

        /**
         * 登陆日志 多线程处理
         */
        private static final ThreadPoolExecutor EXCEPTION_LOG = new ThreadPoolExecutor(1,2,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.EXCEPTION_LOG_THREAD),(r, executor) -> {
                    log.warn("异常日志的线程池已满,data:[{}]",((AbstractTask)r).getData());
        });
    }

    /**
     * 添加操作日志
     * @param task 任务
     */
    public static void executeOperateLog(AbstractTask task){
        ExecutorHolder.OPERATE_LOG.execute(task);
    }

    /**
     * 添加登陆日志
     * @param task 任务
     */
    public static void executeLoginLog(AbstractTask task){
        ExecutorHolder.LOGIN_LOG.execute(task);
    }

    /**
     * 添加异常日志
     * @param task 任务
     */
    public static void executeExceptionLog(AbstractTask task){
        ExecutorHolder.EXCEPTION_LOG.execute(task);
    }
}
