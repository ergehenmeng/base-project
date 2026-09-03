package com.eghm.foundation.web.utility;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 内存缓存工具类, 注意: 这个是单机版, 后续可改造为 Caffeine + Redis分布式缓存 这种支持二级缓存方式
 *
 * @author 二哥很猛
 * @since 2024/9/4
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheUtil {

    /**
     * 双因子验证第一步缓存数据
     */
    public static final Cache<String, Long> TOTP_CACHE = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(10000).build();
    
    /**
     * 用户权限缓存
     */
    public static final Cache<String, List<String>> PERMISSION_CACHE = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(1000).build();
}
