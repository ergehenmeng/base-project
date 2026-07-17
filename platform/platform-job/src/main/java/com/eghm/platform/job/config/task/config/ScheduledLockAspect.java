package com.eghm.platform.job.config.task.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.foundation.web.config.log.LogTraceHolder;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.lock.RedisLock;
import com.eghm.foundation.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @since 2023/7/3
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class ScheduledLockAspect {

    private final RedisLock redisLock;

    private final AlarmService alarmService;

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled) && within(com.eghm.platform.job.config.task..*)")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 类名@方法名
        String lockKey = joinPoint.getSignature().getDeclaringType().getName() + CommonConstant.SPECIAL_SPLIT + joinPoint.getSignature().getName();
        return redisLock.lock(lockKey, CommonConstant.SCHEDULED_MAX_LOCK_TIME, () -> {
            LogTraceHolder.putTraceId(StringUtil.randomHex(16));
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                log.error("定时任务处理失败", e);
                alarmService.sendMsg(String.format("@Scheduled定时任务处理失败[%s], 错误信息:%s", lockKey, ExceptionUtil.stacktraceToString(e)));
            } finally {
                LogTraceHolder.clear();
            }
            return null;
        });
    }
}
