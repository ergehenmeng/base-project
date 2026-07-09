package com.eghm.configuration.ratelimit;

import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 基于 resilience4j 的 IP 维度限流拦截器
 *
 * <p>原理:使用 RateLimiterRegistry 按 "限流器名+维度值" 动态创建 RateLimiter,
 * 通过 Caffeine 缓存动态创建的 RateLimiter 实例,防止无限 IP 导致的内存泄漏
 *
 * <p>被 {@code @RateLimit} 注解的方法在 preHandle 阶段检查,
 * 若获取令牌失败则直接返回 429 + 统一错误格式
 *
 * @author eghm
 */
@Slf4j
public class RateLimitInterceptor implements InterceptorAdapter {

    /**
     * 限流器缓存上限(防止恶意 IP 撑爆内存)
     * 配合 expireAfterAccess 自动清理长时间未访问的 IP
     */
    private static final int RATE_LIMITER_CACHE_SIZE = 50_000;

    /**
     * 限流器在缓存中的存活时间(超过该时间未访问则驱逐)
     * 该值应略大于限流刷新周期,避免频繁重建 RateLimiter
     */
    private static final long RATE_LIMITER_CACHE_TTL_MINUTES = 10;

    /**
     * 动态创建的 per-scope RateLimiter 缓存
     * key: fullName = "{rateLimiterName}#{scopeValue}",如 "captcha#ip:1.2.3.4"
     */
    private final Cache<String, RateLimiter> rateLimiterCache;

    private final RateLimiterRegistry rateLimiterRegistry;

    public RateLimitInterceptor(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.rateLimiterCache = Caffeine.newBuilder()
                .maximumSize(RATE_LIMITER_CACHE_SIZE)
                .expireAfterAccess(RATE_LIMITER_CACHE_TTL_MINUTES, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public boolean beforeHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,
                                @Nonnull Object handler) throws IOException {
        com.eghm.annotation.RateLimiter rateLimit = this.getAnnotation(handler, com.eghm.annotation.RateLimiter.class);
        if (rateLimit == null) {
            return true;
        }
        String scopeValue = this.resolveScope(request, rateLimit.scope());
        String fullName = rateLimit.value() + "#" + scopeValue;
        try {
            RateLimiter limiter = this.getOrCreateLimiter(fullName);
            if (!limiter.acquirePermission()) {
                log.warn("触发限流 [{}] fullName=[{}] scope=[{}] ip=[{}]",
                        request.getRequestURI(), fullName, rateLimit.scope(), IpUtil.getIpAddress(request));
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                WebUtil.printJson(response, ErrorCode.TOO_MANY_REQUESTS);
                return false;
            }
        } catch (Exception e) {
            log.error("限流组件异常 fullName=[{}]", fullName, e);
            return true;
        }
        return true;
    }

    /**
     * 从缓存中获取或创建 per-scope RateLimiter
     * 命名格式: "{rateLimiterName}#{scopeValue}"
     * 由于 RateLimiterRegistry 自身也有缓存,这里 Caffeine 主要是为了快速查找 + 限上限
     */
    private RateLimiter getOrCreateLimiter(String fullName) {
        RateLimiter limiter = rateLimiterCache.get(fullName, name -> rateLimiterRegistry.rateLimiter(name));
        if (limiter == null) {
            limiter = rateLimiterRegistry.rateLimiter(fullName);
            rateLimiterCache.put(fullName, limiter);
        }
        return limiter;
    }

    /**
     * 根据 scope 解析限流维度值
     */
    private String resolveScope(HttpServletRequest request, com.eghm.annotation.RateLimiter.Scope scope) {
        if (scope == com.eghm.annotation.RateLimiter.Scope.GLOBAL) {
            return "global";
        }
        if (scope == com.eghm.annotation.RateLimiter.Scope.USER) {
            UserToken user = SecurityHolder.getUser();
            if (user != null) {
                return "u:" + user.getId();
            }
        }
        return "ip:" + IpUtil.getIpAddress(request);
    }
}
