package com.eghm.configuration.task.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.lock.RedisLock;
import com.eghm.application.system.service.SysTaskLogService;
import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2019/9/6 15:27
 */
@Slf4j
public class Invoker implements Runnable {

    private final Object bean;

    private final Method method;

    private final ScheduleBean dispatch;

    private final RedisLock redisLock;

    private final AlarmService alarmService;

    private final SysTaskLogService sysTaskLogService;

    Invoker(ScheduleBean dispatch, RedisLock redisLock, AlarmService alarmService, SysTaskLogService sysTaskLogService) {
        this.dispatch = dispatch;
        this.redisLock = redisLock;
        this.alarmService = alarmService;
        this.sysTaskLogService = sysTaskLogService;
        try {
            this.bean = SpringUtil.getBean(dispatch.getBeanName());
            this.method = this.findMethod(dispatch, bean);
        } catch (Exception e) {
            log.error("系统中不存在指定的类或该方法 [{}] [{}] 方法入参: [{}]", dispatch.getBeanName(), dispatch.getMethodName(), dispatch.getArgs(), e);
            throw new BusinessException(ErrorCode.TASK_CONFIG_ERROR);
        }
    }

    @Override
    public void run() {
        LogTraceHolder.putTraceId(StringUtil.randomHex(16));
        SysTaskLog.SysTaskLogBuilder builder = SysTaskLog.builder();
        String key = dispatch.getBeanName() + CommonConstant.SPECIAL_SPLIT + dispatch.getMethodName();
        LocalDateTime start = LocalDateTime.now();
        long startTime = System.currentTimeMillis();
        try {
            // 外层加锁防止多实例运行时有并发执行问题, 幂等由业务进行控制
            redisLock.lock(key, dispatch.getLockTime(), () -> ReflectUtil.invoke(bean, method, dispatch.getArgs()));
        } catch (Exception e) {
            // 异常时记录日志并发送邮件
            log.error("定时任务执行异常 bean:[{}] method: [{}]", dispatch.getBeanName(), dispatch.getMethodName(), e);
            String errorMsg = ExceptionUtils.getStackTrace(e);
            builder.errorMsg(errorMsg);
            builder.state(false);
            alarmService.sendMsg(String.format("自定义定时任务执行失败[%s], 错误信息:%s", key, ExceptionUtil.stacktraceToString(e)));
        } finally {
            if (Boolean.TRUE.equals(dispatch.getLog())) {
                // 每次执行的日志都记入定时任务日志
                builder.beanName(dispatch.getBeanName()).methodName(dispatch.getMethodName()).args(dispatch.getArgs()).ip(NetUtil.getLocalhostStr());
                builder.elapsedTime(System.currentTimeMillis() - startTime);
                builder.startTime(start);
                sysTaskLogService.addTaskLog(builder.build());
            }
            LogTraceHolder.clear();
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
    private Method findMethod(ScheduleBean task, Object bean) throws NoSuchMethodException {
        Class<?> cls = AopUtils.isAopProxy(bean) ? bean.getClass().getSuperclass() : bean.getClass();
        if (isBlank(task.getArgs())) {
            return cls.getMethod(task.getMethodName());
        }
        return cls.getMethod(task.getMethodName(), String.class);
    }
}
