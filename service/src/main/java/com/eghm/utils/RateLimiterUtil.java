package com.eghm.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 限流工具类(基于 resilience4j)
 *
 * @author eghm
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RateLimiterUtil {

    /**
     * 限流器缓存上限(防止 name 空间无限扩张)
     */
    private static final int RATE_LIMITER_CACHE_SIZE = 50_000;

    /**
     * 限流器在缓存中的存活时间(超过该时间未访问则驱逐)
     */
    private static final long RATE_LIMITER_CACHE_TTL_MINUTES = 10;

    /**
     * 限流器缓存,key=调用方传入的 name,value=按参数程序化创建的 RateLimiter
     */
    private static final Cache<String, RateLimiter> CACHE = Caffeine.newBuilder().maximumSize(RATE_LIMITER_CACHE_SIZE).expireAfterAccess(RATE_LIMITER_CACHE_TTL_MINUTES, TimeUnit.MINUTES).build();

    /**
     * 按 name 取出或创建 RateLimiter(底层 API)
     *
     * @param name          限流器名称,相同 name 共享 RateLimiter 实例
     * @param limit         时间窗口内最大访问次数
     * @param refreshPeriod 时间窗口
     * @param timeout       获取许可的超时时间,推荐 Duration.ZERO 表示不等待直接返回
     * @return 缓存中或新创建的 RateLimiter
     */
    public static RateLimiter getOrCreate(String name, int limit, Duration refreshPeriod, Duration timeout) {
        return CACHE.get(name, k -> RateLimiter.of(k, RateLimiterConfig.custom().limitForPeriod(limit).limitRefreshPeriod(refreshPeriod).timeoutDuration(timeout).build()));
    }

    /**
     * 报警场景便捷方法:装饰 Runnable + 捕获 RequestNotPermitted
     *
     * @param name          限流器名称
     * @param limit         时间窗口内最大访问次数
     * @param refreshPeriod 时间窗口
     * @param timeout       获取许可的超时时间
     * @param runnable      实际业务逻辑
     * @param limitHandler  被限流时的处理(可为 null,默认 warn 日志)
     */
    public static void execute(String name, int limit, Duration refreshPeriod, Duration timeout,
                               Runnable runnable, Consumer<RequestNotPermitted> limitHandler) {
        RateLimiter limiter = getOrCreate(name, limit, refreshPeriod, timeout);
        Runnable decorated = RateLimiter.decorateRunnable(limiter, runnable);
        try {
            decorated.run();
        } catch (RequestNotPermitted e) {
            if (limitHandler != null) {
                limitHandler.accept(e);
            } else {
                log.warn("限流触发 [{}] limit=[{}/{}]", name, limit, refreshPeriod);
            }
        }
    }

    /**
     * AOP/接口场景:同步尝试获取许可
     *
     * @return true 放行 / false 被限流
     */
    public static boolean tryAcquire(String name, int limit, Duration refreshPeriod) {
        RateLimiter limiter = getOrCreate(name, limit, refreshPeriod, Duration.ZERO);
        return limiter.acquirePermission();
    }
}
