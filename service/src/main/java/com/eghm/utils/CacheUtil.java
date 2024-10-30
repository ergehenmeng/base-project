package com.eghm.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

import static com.eghm.constants.CommonConstant.SMS_CODE_EXPIRE;
import static com.eghm.constants.CommonConstant.SUBMIT_INTERVAL;

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
     * post请求间隔限制
     */
    public static final Cache<String, Boolean> INTERVAL_CACHE = Caffeine.newBuilder().expireAfterWrite(SUBMIT_INTERVAL, TimeUnit.MILLISECONDS).maximumSize(2000).build();

    /**
     * 文件单日上传限制
     */
    public static final Cache<String, Long> UPLOAD_LIMIT_CACHE = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).maximumSize(10000).build();

    /**
     * 验证码验证次数上限限制
     */
    public static final Cache<String, Long> SMS_VERIFY_CACHE = Caffeine.newBuilder().expireAfterWrite(SMS_CODE_EXPIRE, TimeUnit.SECONDS).maximumSize(10000).build();

}
