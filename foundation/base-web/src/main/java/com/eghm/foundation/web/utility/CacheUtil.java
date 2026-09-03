package com.eghm.foundation.web.utility;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.eghm.foundation.core.constants.CommonConstant.SMS_CODE_EXPIRE;
import static com.eghm.foundation.core.constants.CommonConstant.SUBMIT_INTERVAL;

/**
 * 内存缓存工具类, 注意: 这个是单机版, 后续可改造为 Caffeine + Redis分布式缓存 这种支持二级缓存方式
 *
 * @author 二哥很猛
 * @since 2024/9/4
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtil {

    /**
     * post请求间隔限制
     */
    public static final Cache<String, Boolean> INTERVAL_CACHE = Caffeine.newBuilder().expireAfterWrite(SUBMIT_INTERVAL, TimeUnit.MILLISECONDS).maximumSize(50000).build();

    /**
     * 文件单日上传限制
     */
    public static final Cache<String, Long> UPLOAD_LIMIT_CACHE = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).maximumSize(20000).build();

    /**
     * 验证码验证次数上限限制
     */
    public static final Cache<String, Integer> SMS_VERIFY_CACHE = Caffeine.newBuilder().expireAfterWrite(SMS_CODE_EXPIRE, TimeUnit.SECONDS).maximumSize(20000).build();

    /**
     * 双因子验证第一步缓存数据
     */
    public static final Cache<String, Long> TOTP_CACHE = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(10000).build();
    
    /**
     * 用户权限缓存
     */
    public static final Cache<String, List<String>> PERMISSION_CACHE = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(1000).build();
}
