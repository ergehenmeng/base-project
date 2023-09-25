package com.eghm.configuration.task.config;

import com.eghm.constant.CommonConstant;
import com.eghm.service.cache.RedisLock;
import com.eghm.service.sys.DingTalkService;
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

    private final DingTalkService dingTalkService;

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled) && within(com.eghm.configuration.task..*)")
    public Object around(@NotNull ProceedingJoinPoint joinPoint) {
        // 类名@方法名
        String lockKey = joinPoint.getSignature().getDeclaringType().getName() + CommonConstant.SPECIAL_SPLIT + joinPoint.getSignature().getName();
        return redisLock.lock(lockKey, CommonConstant.SCHEDULED_MAX_LOCK_TIME, () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                log.error("定时任务处理失败", e);
                dingTalkService.sendMsg(String.format("@Scheduled定时任务处理失败[%s]", lockKey));
            }
            return null;
        });
    }
}
