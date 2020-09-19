package com.eghm.configuration.task.config;

import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.TaskLog;
import com.eghm.service.common.TaskAlarmService;
import com.eghm.service.common.TaskLogService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:27
 */
@Slf4j
public class RunnableTask implements Runnable {

    /**
     * 具体业务实现
     */
    private Task task;

    /**
     * 日志记录
     */
    private TaskLogService taskLogService;

    /**
     * 执行任务时说明信息
     */
    private TaskDetail detail;

    RunnableTask(TaskDetail detail) {
        this.detail = detail;
        initBeans();
    }


    @Override
    public void run() {
        Date now = DateUtil.getNow();
        long startTime = now.getTime();
        TaskLog.TaskLogBuilder builder = TaskLog.builder().nid(detail.getNid()).beanName(detail.getBeanName()).ip(IpUtil.getLocalIp()).startTime(now);
        try {
            // 任务幂等由业务来决定
            task.execute();
        } catch (RuntimeException e) {
            // 异常时记录日志并发送邮件
            log.error("定时任务执行异常 nid:[{}] bean:[{}]", detail.getNid(), detail.getBeanName(), e);
            builder.state(false);
            String errorMsg = ExceptionUtils.getMessage(e);
            builder.errorMsg(errorMsg);
            this.sendExceptionEmail(errorMsg);
        } finally {
            // 每次执行的日志都记入定时任务日志
            long endTime = System.currentTimeMillis();
            builder.elapsedTime(endTime - startTime);
            taskLogService().addTaskLog(builder.build());
        }
    }

    private TaskLogService taskLogService() {
        if (taskLogService != null) {
            return taskLogService;
        }
        this.taskLogService = (TaskLogService) SpringContextUtil.getBean("taskLogService");
        return taskLogService;
    }

    /**
     * 发送异常通知邮件
     * @param errorMsg msg
     */
    private void sendExceptionEmail(String errorMsg) {
        TaskAlarmService taskAlarmService = (TaskAlarmService) SpringContextUtil.getBean("TaskAlarmService");
        taskAlarmService.noticeAlarm(detail, errorMsg);
    }

    /**
     * 从Spring容器中获取Bean
     */
    private void initBeans() {
        task = (Task) SpringContextUtil.getBean(detail.getBeanName());
    }
}
