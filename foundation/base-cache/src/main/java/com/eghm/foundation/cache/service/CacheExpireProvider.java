package com.eghm.foundation.cache.service;

@FunctionalInterface
public interface CacheExpireProvider {
    
    /**
     * 缓存默认过期时间
     *
     * @return 秒
     */
    long getExpireSeconds();
}
