package com.eghm.service.cache.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.ext.AsyncResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 *
 * @author 二哥很猛
 * @date 2018/11/21 16:28
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private Gson gson;

    @Override
    public void setValue(String key, Object value) {
        this.setValue(key, value, systemConfigApi.getLong(ConfigConstant.CACHE_EXPIRE));
    }

    @Override
    public void setValue(String key, Object value, long expire) {
        if (value instanceof String) {
            stringRedisTemplate.opsForValue().set(key, (String)value, expire, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, gson.toJson(value), expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public void setValue(String key, Object value, Date expireTime) {
        this.setValue(key, value);
        stringRedisTemplate.expireAt(key, expireTime);
    }

    @Override
    public void cacheAsyncResponse(AsyncResponse response) {
        this.setValue(CacheConstant.ASYNC_RESPONSE + response.getKey(), response);
    }

    @Override
    public AsyncResponse getAsyncResponse(String key) {
        return this.getValue(CacheConstant.ASYNC_RESPONSE + key, AsyncResponse.class);
    }

    @Override
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T getValue(String key, Class<T> cls) {
        String o = stringRedisTemplate.opsForValue().get(key);
        if (o != null) {
            return gson.fromJson(o, cls);
        }
        return null;
    }

    @Override
    public int keySize(String key) {
        Set<String> keys = stringRedisTemplate.keys(key);
        if (!CollectionUtils.isEmpty(keys)) {
            return keys.size();
        }
        return 0;
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
