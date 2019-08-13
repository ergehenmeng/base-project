package com.fanyin.service.cache.impl;

import com.fanyin.common.constant.RedisConstant;
import com.fanyin.service.cache.ClearCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 全局清除缓存
 * @author 二哥很猛
 * @date 2019/1/14 13:52
 */
@Service("clearCacheService")
public class ClearCacheServiceImpl implements ClearCacheService{


    @Override
    @CacheEvict(cacheNames = RedisConstant.SYSTEM_CONFIG,allEntries = true)
    public void clearSystemConfig() {
    }


    @Override
    @CacheEvict(cacheNames = RedisConstant.SYSTEM_DICT,allEntries = true)
    public void clearSystemDict() {
    }

    @Override
    @CacheEvict(cacheNames = RedisConstant.ACCESS_TOKEN,allEntries = true)
    public void clearAccessToken() {
    }

    @Override
    @CacheEvict(cacheNames = RedisConstant.ASYNC_RESPONSE,allEntries = true)
    public void clearAsyncResponse() {
    }
}
