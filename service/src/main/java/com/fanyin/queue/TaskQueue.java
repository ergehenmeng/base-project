package com.fanyin.queue;

import com.fanyin.constants.TaskConstant;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 二哥很猛
 * @date 2018/11/16 17:25
 */
public class TaskQueue {

    private static class TaskQueueHolder{

        /**
         * 操作日志 多线程处理
         */
        private static final ThreadPoolExecutor OPERATION = new ThreadPoolExecutor(1,2,30,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),r -> new Thread(r,TaskConstant.OPERATION_THREAD));
    }

    /**
     * 操作日志
     * @param task 任务
     */
    public static void executeOperation(AbstractTask task){
        TaskQueueHolder.OPERATION.execute(task);
    }

}
