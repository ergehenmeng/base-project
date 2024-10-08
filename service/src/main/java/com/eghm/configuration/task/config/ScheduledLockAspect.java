package com.eghm.configuration.task.config;

import com.eghm.common.AlarmService;
import com.eghm.constants.CommonConstant;
import com.eghm.lock.RedisLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
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

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled) && within(com.eghm.configuration.task..*)")
    public Object around(@NotNull ProceedingJoinPoint joinPoint) {
        // 类名@方法名
        String lockKey = joinPoint.getSignature().getDeclaringType().getName() + CommonConstant.SPECIAL_SPLIT + joinPoint.getSignature().getName();
        return redisLock.lock(lockKey, CommonConstant.SCHEDULED_MAX_LOCK_TIME, () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                log.error("定时任务处理失败", e);
                alarmService.sendMsg(String.format("@Scheduled定时任务处理失败[%s]", lockKey));
            }
            return null;
        });
    }
}
