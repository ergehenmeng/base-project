package com.eghm.configuration.task.config;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.model.SysTaskLog;
import com.eghm.service.cache.RedisLock;
import com.eghm.service.common.SysTaskLogService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/9/6 15:27
 */
@Slf4j
public class RunnableTask implements Runnable {

    private final Task task;

    private final Object bean;

    private final Method method;

    private final RedisLock redisLock;

    private final DingTalkService dingTalkService;

    private final SysTaskLogService sysTaskLogService;

    RunnableTask(Task task) {
        this.task = task;
        try {
            this.bean = SpringContextUtil.getBean(task.getBeanName());
            this.redisLock = SpringContextUtil.getBean(RedisLock.class);
            this.method = this.findMethod(task, bean);
            this.sysTaskLogService = SpringContextUtil.getBean(SysTaskLogService.class);
            this.dingTalkService = SpringContextUtil.getBean(DingTalkService.class);
        } catch (Exception e) {
            log.error("系统中不存在指定的类或该方法 [{}] [{}] [{}]", task.getBeanName(), task.getMethodName(), task.getArgs(), e);
            throw new BusinessException(ErrorCode.TASK_CONFIG_ERROR);
        }
    }

    @Override
    public void run() {
        SysTaskLog.SysTaskLogBuilder builder = SysTaskLog.builder().beanName(task.getBeanName()).methodName(task.getMethodName()).args(task.getArgs()).ip(IpUtil.getLocalIp());
        String key = task.getBeanName() + CommonConstant.SPECIAL_SPLIT + task.getMethodName();
        LocalDateTime start = LocalDateTime.now();
        long startTime = System.currentTimeMillis();
        try {
            // 外层加锁防止多实例运行时有并发执行问题, 幂等由业务进行控制
            redisLock.lockVoid(key, task.getLockTime(), () -> ReflectUtil.invoke(bean, method, task.getArgs()));
        } catch (Exception e) {
            // 异常时记录日志并发送邮件
            log.error("定时任务执行异常 bean:[{}] method: [{}]", task.getBeanName(), task.getMethodName(), e);
            String errorMsg = ExceptionUtils.getStackTrace(e);
            builder.errorMsg(errorMsg);
            builder.state(false);
            dingTalkService.sendMsg(String.format("自定义定时任务执行失败[%s]", key));
        } finally {
            // 每次执行的日志都记入定时任务日志
            long endTime = System.currentTimeMillis();
            builder.elapsedTime(endTime - startTime);
            builder.startTime(start);
            sysTaskLogService.addTaskLog(builder.build());
        }
    }

    /**
     * 根据bean和methodName查询方法
     *
     * @param task 任务配置, 如果配置参考有参数, 默认为有参方法, 否则为无参方法
     * @param bean bean
     * @return 方法
     * @throws NoSuchMethodException e
     */
    private Method findMethod(Task task, Object bean) throws NoSuchMethodException {
        Class<?> cls = AopUtils.isAopProxy(bean) ? bean.getClass().getSuperclass() : bean.getClass();
        if (StrUtil.isBlank(task.getArgs())) {
            return cls.getMethod(task.getMethodName());
        }
        return cls.getMethod(task.getMethodName(), String.class);
    }
}
