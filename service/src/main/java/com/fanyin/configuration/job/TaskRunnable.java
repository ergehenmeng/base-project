package com.fanyin.configuration.job;

import com.fanyin.common.utils.DateUtil;
import com.fanyin.dao.model.business.TaskLog;
import com.fanyin.service.common.TaskLogService;
import com.fanyin.utils.IpUtil;
import com.fanyin.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:27
 */
@Slf4j
public class TaskRunnable implements Runnable {

    /**
     * 具体业务实现
     */
    private Task task;

    /**
     * 日志记录
     */
    private TaskLogService taskLogService;

    /**
     * 任务bean
     */
    private String beanName;

    /**
     * 任务nid(唯一标示符)
     */
    private String nid;

    TaskRunnable(String beanName, String nid) {
        this.beanName = beanName;
        this.nid = nid;
    }

    @Override
    public void run() {
        Date now = DateUtil.getNow();
        long startTime = now.getTime();
        TaskLog.TaskLogBuilder builder = TaskLog.builder().nid(nid).beanName(beanName).ip(IpUtil.getLocalIp()).startTime(now);
        try {
            getTaskBean().execute();
        } catch (Exception e) {
            log.error("定时任务执行异常 nid:[{}] bean:[{}]", nid, beanName, e);
            builder.state(false);
            builder.errorMsg(ExceptionUtils.getMessage(e));
        } finally {
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

    private Task getTaskBean() {
        if (task != null) {
            return task;
        }
        //必须保证bean名称正确
        this.task = (Task) SpringContextUtil.getBean(beanName);
        return task;
    }
}
