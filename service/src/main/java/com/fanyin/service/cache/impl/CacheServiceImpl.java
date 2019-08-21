package com.fanyin.service.cache.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.model.ext.Async;
import com.fanyin.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 * @author 二哥很猛
 * @date 2018/11/21 16:28
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 默认过期时间 1800s
     */
    private static final long EXPIRE = 1800;

    @Override
    public void setValue(String key, Object value) {
        this.setValue(key, value,EXPIRE);
    }

    @Override
    public void setValue(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value,expire, TimeUnit.SECONDS);
    }

    @Override
    public void setValue(String key, Object value, Date expireTime) {
        this.setValue(key, value);
        redisTemplate.expireAt(key,expireTime);
    }

    @Override
    @CachePut(cacheNames = CacheConstant.ASYNC_RESPONSE,key = "#response.key",cacheManager = "smallCacheManager")
    public void cacheAsyncResponse(Async response) {
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.ASYNC_RESPONSE,key = "#p0",cacheManager = "smallCacheManager")
    public Async getAsyncResponse(String key) {
        return null;
    }

    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public int keySize(String key) {
        Set<String> keys = redisTemplate.keys(key);
        if(!CollectionUtils.isEmpty(keys)){
            return keys.size();
        }
        return 0;
    }
}
