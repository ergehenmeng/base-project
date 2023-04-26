package com.eghm.configuration.task.config;

import cn.hutool.core.util.ReflectUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.model.SysTaskLog;
import com.eghm.service.cache.LockService;
import com.eghm.service.common.SysTaskLogService;
import com.eghm.service.common.TaskAlarmService;
import com.eghm.utils.DateUtil;
import com.eghm.utils.IpUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;
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
    private final Object bean;

    /**
     * 锁
     */
    private final LockService lockService;

    /**
     * 方法名
     */
    private final Method method;

    /**
     * 日志记录
     */
    private SysTaskLogService sysTaskLogService;

    /**
     * 执行任务时说明信息
     */
    private final Task task;

    RunnableTask(Task task) {
        this.task = task;
        try {
            this.bean = SpringContextUtil.getBean(task.getBeanName());
            this.lockService = SpringContextUtil.getBean(LockService.class);
            this.method = AopUtils.isAopProxy(bean) ? bean.getClass().getSuperclass().getMethod(task.getMethodName(), String.class) : bean.getClass().getMethod(task.getMethodName(), String.class);
        } catch (Exception e) {
            log.error("系统中不存在指定的类或该方法 [{}] [{}]", task.getBeanName(), task.getMethodName(), e);
            throw new BusinessException(ErrorCode.TASK_CONFIG_ERROR);
        }
    }

    @Override
    public void run() {
        Date now = DateUtil.getNow();
        long startTime = now.getTime();
        SysTaskLog.SysTaskLogBuilder builder = SysTaskLog.builder().beanName(task.getBeanName()).methodName(task.getMethodName()).args(task.getArgs()).ip(IpUtil.getLocalIp());
        try {
            // 外层加锁防止多部分运行时有并发执行问题, 幂等由业务进行控制
            lockService.lock(task.getBeanName() + ":" + task.getMethodName(), task.getLockTime(), () -> {
                ReflectUtil.invoke(bean, method, task.getArgs());
                return null;
            });
        } catch (Exception e) {
            // 异常时记录日志并发送邮件
            log.error("定时任务执行异常 bean:[{}] method: [{}]", task.getBeanName(), task.getMethodName(), e);
            builder.state(false);
            String errorMsg = ExceptionUtils.getStackTrace(e);
            builder.errorMsg(errorMsg);
            this.sendExceptionEmail(errorMsg);
        } finally {
            // 每次执行的日志都记入定时任务日志
            long endTime = System.currentTimeMillis();
            builder.elapsedTime(endTime - startTime);
            taskLogService().addTaskLog(builder.build());
        }
    }

    private SysTaskLogService taskLogService() {
        if (sysTaskLogService != null) {
            return sysTaskLogService;
        }
        this.sysTaskLogService = (SysTaskLogService) SpringContextUtil.getBean("sysTaskLogService");
        return sysTaskLogService;
    }

    /**
     * 发送异常通知邮件
     * @param errorMsg msg
     */
    private void sendExceptionEmail(String errorMsg) {
        TaskAlarmService taskAlarmService = (TaskAlarmService) SpringContextUtil.getBean("taskAlarmService");
        taskAlarmService.noticeAlarm(task, errorMsg);
    }

}
