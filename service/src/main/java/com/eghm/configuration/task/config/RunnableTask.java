package com.eghm.configuration.task.config;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.EmailType;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.common.EmailService;
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
     * 任务bean
     */
    private String beanName;

    /**
     * 任务nid(唯一标示符)
     */
    private String nid;

    /**
     * 报警邮箱地址
     */
    private String alarmEmail;

    RunnableTask(String nid, String beanName, String alarmEmail) {
        this.nid = nid;
        this.beanName = beanName;
        this.alarmEmail = alarmEmail;
        initBeans();
    }

    RunnableTask(String nid, String beanName) {
        this(nid, beanName, null);
    }

    @Override
    public void run() {
        Date now = DateUtil.getNow();
        long startTime = now.getTime();
        TaskLog.TaskLogBuilder builder = TaskLog.builder().nid(nid).beanName(beanName).ip(IpUtil.getLocalIp()).startTime(now);
        try {
            // 任务幂等由业务来决定
            task.execute();
        } catch (RuntimeException e) {
            // 异常时记录日志并发送邮件
            log.error("定时任务执行异常 nid:[{}] bean:[{}]", nid, beanName, e);
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
        if (StrUtil.isNotBlank(alarmEmail)) {
            EmailService emailService = (EmailService) SpringContextUtil.getBean("emailService");
            SendEmail sendEmail = new SendEmail();
            sendEmail.setType(EmailType.TASK_ALARM);
            sendEmail.setEmail(alarmEmail);
            sendEmail.put("errorMsg", errorMsg);
            sendEmail.put("nid", nid);
            emailService.sendEmail(sendEmail);
        }
    }

    /**
     * 从Spring容器中获取Bean
     */
    private void initBeans() {
        task = (Task) SpringContextUtil.getBean(beanName);
    }
}
