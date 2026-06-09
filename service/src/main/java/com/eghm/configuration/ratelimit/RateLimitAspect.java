package com.eghm.configuration.ratelimit;

import com.eghm.annotation.RateLimiter;
import com.eghm.utils.RateLimiterUtil;
import com.eghm.configuration.security.ApiHolder;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.IpUtil;
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
 *
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
        String scopeValue = this.resolveScope(rateLimiter.scope());
        String key = rateLimiter.value() + "#" + scopeValue;
        try {
            if (!RateLimiterUtil.tryAcquire(key, rateLimiter.limit(), Duration.ofSeconds(rateLimiter.period()))) {
                log.warn("触发限流 [{}.{}] key=[{}] limit=[{}/{}s] scope=[{}]",
                        joinPoint.getSignature().getDeclaringType().getSimpleName(),
                        joinPoint.getSignature().getName(),
                        key, rateLimiter.limit(), rateLimiter.period(), rateLimiter.scope());
                throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS);
            }
            return joinPoint.proceed();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("限流组件异常 key=[{}]", key, e);
            if (rateLimiter.fallbackPass()) {
                return joinPoint.proceed();
            }
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

    private String currentRequestUri() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest().getRequestURI();
    }

    private jakarta.servlet.http.HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }
}
