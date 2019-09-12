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
public class TaskQueue {

    private static class TaskQueueHolder{

        /**
         * 操作日志 多线程处理
         */
        private static final ThreadPoolExecutor OPERATE_LOG = new ThreadPoolExecutor(5,10,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.OPERATE_LOG_THREAD));

        /**
         * 登陆日志 多线程处理
         */
        private static final ThreadPoolExecutor LOGIN_LOG = new ThreadPoolExecutor(1,2,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.LOGIN_LOG_THREAD),(r, executor) -> {
            log.warn("登陆日志线程池已满,data:[{}]",((AbstractTask)r).getData());
        });
    }

    /**
     * 操作日志
     * @param task 任务
     */
    public static void executeOperateLog(AbstractTask task){
        TaskQueueHolder.OPERATE_LOG.execute(task);
    }

    /**
     * 登陆日志线程池
     * @param task 任务
     */
    public static void executeLoginLog(AbstractTask task){
        TaskQueueHolder.LOGIN_LOG.execute(task);
   }

}
