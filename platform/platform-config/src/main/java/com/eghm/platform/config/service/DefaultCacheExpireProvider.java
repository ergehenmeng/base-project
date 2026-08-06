package com.eghm.platform.config.service;

import com.eghm.foundation.cache.service.CacheExpireProvider;
import com.eghm.foundation.core.constants.ConfigConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.foundation.core.constants.CacheConstant.DEFAULT_EXPIRE;

@Service
@RequiredArgsConstructor
public class DefaultCacheExpireProvider implements CacheExpireProvider {

    private final SysConfigApi sysConfigApi;

    @Override
    public long getExpireSeconds() {
        return sysConfigApi.getLong(ConfigConstant.CACHE_EXPIRE, DEFAULT_EXPIRE);
    }
}
