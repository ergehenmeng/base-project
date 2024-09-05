package com.eghm.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

import static com.eghm.constant.CommonConstant.SUBMIT_INTERVAL;

/**
 * 内存缓存工具类
 *
 * @author 二哥很猛
 * @since 2024/9/4
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtil {

    /**
     * 登录失败次数过多锁定
     */
    public static final Cache<String, Integer> LOGIN_LOCK_CACHE = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).build();

    /**
     * 验证码缓存 默认1分钟失效
     */
    public static final Cache<String, String> CAPTCHA_CACHE = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(1000).build();

    /**
     * post请求间隔限制
     */
    public static final Cache<String, Boolean> INTERVAL_CACHE = Caffeine.newBuilder().expireAfterWrite(SUBMIT_INTERVAL, TimeUnit.MILLISECONDS).maximumSize(2000).build();
}
