package com.eghm.foundation.web.config.ratelimit;

import com.eghm.foundation.core.annotation.RateLimiter;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.foundation.web.utility.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;

/**
 * 基于 AOP + 限流工具类的限流切面
 * 注意: 这个是单机版, 后续可使用redis-lua脚本实现分布式配置
 * @author eghm
 */
@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    /**
     * 拦截所有标注了 {@link RateLimiter} 的方法
     */
    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        String key = this.generateKey(rateLimiter);
        this.acquirePermit(key, rateLimiter);
        return joinPoint.proceed();
    }

    /**
     * 获取限流许可,失败时抛业务异常;限流组件异常时按 fallbackPass 决定放行/拒绝
     */
    private void acquirePermit(String key, RateLimiter rateLimiter) {
        if (!RateLimiterUtil.tryAcquire(key, rateLimiter.limit(), Duration.ofSeconds(rateLimiter.period()))) {
            log.warn("触发限流 key=[{}] limit=[{}/{}s] scope=[{}]", key, rateLimiter.limit(), rateLimiter.period(), rateLimiter.scope());
            throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS);
        }
    }

    /**
     * 解析限流维度值,根据 scope 从请求上下文中提取
     */
    private String resolveScope(RateLimiter.Scope scope) {
        if (scope == RateLimiter.Scope.GLOBAL) {
            return "global";
        }
        String uri = this.currentRequestUri();
        if (scope == RateLimiter.Scope.USER) {
            // USER 维度:优先用用户 ID,降级用 IP
            if (uri != null) {
                if (uri.startsWith(CommonConstant.MANAGE_PREFIX)) {
                    Long userId = SecurityHolder.tryGetUserId();
                    if (userId != null) {
                        return "u:" + userId;
                    }
                } else if (uri.startsWith(CommonConstant.WEBAPP_PREFIX)) {
                    Long memberId = ApiHolder.tryGetMemberId();
                    if (memberId != null) {
                        return "u:" + memberId;
                    }
                }
            }
        }
        // IP 维度 或 USER 维度未登录时
        return "ip:" + IpUtil.getIpAddress(this.currentRequest());
    }
    
    /**
     *生成限流许可 key
     * @param rateLimiter 限流器配置
     * @return key
     */
    private String generateKey(RateLimiter rateLimiter) {
        String scopeValue = this.resolveScope(rateLimiter.scope());
        return "ratelimiter:" + rateLimiter.value() + "#" + scopeValue;
    }

    private String currentRequestUri() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest().getRequestURI();
    }

    private jakarta.servlet.http.HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }
}
