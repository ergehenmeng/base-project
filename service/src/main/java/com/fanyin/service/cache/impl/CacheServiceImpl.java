package com.fanyin.service.cache.impl;

import com.fanyin.common.constant.RedisConstant;
import com.fanyin.ext.Async;
import com.fanyin.ext.AccessToken;
import com.fanyin.service.cache.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 * @author 二哥很猛
 * @date 2018/11/21 16:28
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Override
    @CachePut(cacheNames = RedisConstant.ASYNC_RESPONSE,key = "#response.key",cacheManager = "smallCacheManager")
    public void cacheAsyncResponse(Async response) {
    }

    @Override
    @Cacheable(cacheNames = RedisConstant.ASYNC_RESPONSE,key = "#p0",cacheManager = "smallCacheManager")
    public Async getAsyncResponse(String key) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = RedisConstant.ACCESS_TOKEN,key = "#p0")
    public AccessToken getAccessToken(String accessKey) {
        return null;
    }

    @Override
    @CachePut(cacheNames = RedisConstant.ACCESS_TOKEN,key = "#token.accessKey",cacheManager = "longCacheManager")
    public void cacheAccessToken(AccessToken token) {
    }
}
