package com.eghm.platform.iam.service;

import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.platform.iam.mapper.AuthConfigMapper;
import com.eghm.platform.iam.vo.AuthConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthConfigCacheService {

    private final AuthConfigMapper authConfigMapper;

    @Cacheable(cacheNames = CacheConstant.AUTH_CONFIG, key = "#p0", unless = "#result == null", cacheManager = "longCacheManager")
    public AuthConfigVO getByAppId(String appId) {
        return authConfigMapper.getByAppId(appId);
    }
}
