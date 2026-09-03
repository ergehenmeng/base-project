package com.eghm.foundation.cache.service.impl;

import com.eghm.foundation.cache.service.CacheExpireProvider;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.constants.LockConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.exception.ParameterException;
import com.eghm.foundation.core.lock.RedisLock;
import com.eghm.foundation.core.service.JsonService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.eghm.foundation.core.constants.CacheConstant.DEFAULT_EXPIRE;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 *
 * @author 二哥很猛
 * @since 2018/11/21 16:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisLock redisLock;

    private final JsonService jsonService;

    private final CacheExpireProvider cacheExpireProvider;

    private final StringRedisTemplate redisTemplate;

    @Override
    public <T> T getValue(String key, TypeReference<T> type, Supplier<T> supplier) {
        String value;
        try {
            value = this.getValue(key);
        } catch (Exception e) {
            if (e instanceof ParameterException) {
                throw e;
            }
            log.warn("获取缓存数据异常", e);
            return supplier.get();
        }
        if (CacheConstant.PLACE_HOLDER.equals(value)) {
            return null;
        }
        if (value != null) {
            return jsonService.fromJson(value, type);
        }
        //缓存数据为空,从数据库获取
        return this.doSupplier(key, supplier);
    }

    /**
     * 调用回调函数获取结果,并将结果缓存
     *
     * @param key      缓存的key
     * @param supplier 会到函数
     * @param <T>      结果类型
     * @return 结果信息
     */
    private <T> T doSupplier(String key, Supplier<T> supplier) {
        T result = redisLock.lock(LockConstant.MUTEX_LOCK + key, CacheConstant.MUTEX_EXPIRE, supplier);
        if (result != null) {
            this.setValue(key, result, cacheExpireProvider.getExpireSeconds());
        } else {
            // 数据库也没有查询到,填充默认值
            this.setValue(key, CacheConstant.PLACE_HOLDER, DEFAULT_EXPIRE);
        }
        return result;
    }

    @Override
    public void setValue(String key, Object value, long expire) {
        this.setValue(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public void setValue(String key, Object value, long expire, TimeUnit unit) {
        if (value == null) {
            log.error("缓存值不能为空 [{}]", key);
            throw new BusinessException(ErrorCode.CACHE_VALUE_NULL);
        }
        if (value instanceof String v) {
            redisTemplate.opsForValue().set(key, v, expire, unit);
        } else {
            redisTemplate.opsForValue().set(key, jsonService.toJson(value), expire, unit);
        }
    }

    @Override
    public boolean exist(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return Objects.equals(Boolean.TRUE, hasKey);
    }

    @Override
    public String getValue(String key) {
        DataType type = redisTemplate.type(key);
        if (type != DataType.STRING && type != DataType.NONE) {
            throw new ParameterException(ErrorCode.REDIS_KEY_TYPE_ERROR);
        }
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> scan(String key, int limit) {
        ScanOptions options = ScanOptions.scanOptions().match("*" + key + "*").count(limit).build();
        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            return cursor.stream().toList();
        }
    }

    @Override
    public <T> T getValue(String key, Class<T> cls) {
        String o = this.getValue(key);
        if (o != null) {
            return jsonService.fromJson(o, cls);
        }
        return null;
    }

    @Override
    public <T> T getValue(String key, TypeReference<T> type) {
        String value = this.getValue(key);
        if (value != null) {
            return jsonService.fromJson(value, type);
        }
        return null;
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean limit(String key, int maxLimit, long maxTtl) {
        // 数组不设置过期时间,默认最多保留maxLimit个元素
        Long size = redisTemplate.opsForList().size(key);
        String leftPop;
        if (size == null || size < maxLimit || (leftPop = redisTemplate.opsForList().leftPop(key)) == null) {
            redisTemplate.opsForList().rightPush(key, String.valueOf(System.currentTimeMillis()));
            return false;
        }
        // 如果刚好此时,在maxTtl时间内的第一次存储的数据过期了,依旧返回true,不做毫秒值等判断
        if (System.currentTimeMillis() - Long.parseLong(leftPop) < maxTtl) {
            // pop会取出元素,因此此处需要再重新放进去
            redisTemplate.opsForList().leftPush(key, leftPop);
            return true;
        }
        redisTemplate.opsForList().rightPush(key, String.valueOf(System.currentTimeMillis()));
        redisTemplate.expire(key, CommonConstant.LIMIT_MAX_EXPIRE, TimeUnit.SECONDS);
        return false;
    }

    @Override
    public void setHashValue(String key, String hKey, String hValue) {
        redisTemplate.opsForHash().put(key, hKey, hValue);
    }

    @Override
    public void setHashValue(String key, long expire, String hKey, String hValue) {
        redisTemplate.opsForHash().put(key, hKey, hValue);
        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getHashValue(String key, String hKey) {
        return (String) redisTemplate.opsForHash().get(key, hKey);
    }
    

    @Override
    public <T> T getHashValue(String key, String hKey, Class<T> type) {
        String value = this.getHashValue(key, hKey);
        if (value != null) {
            return jsonService.fromJson(value, type);
        }
        return null;
    }

    @Override
    public boolean hasHashKey(String key, String hKey) {
        Boolean hasKey = redisTemplate.opsForHash().hasKey(key, hKey);
        return Objects.equals(Boolean.TRUE, hasKey);
    }

    @Override
    public boolean hasSetKey(String key, Object value) {
        Boolean member = redisTemplate.opsForSet().isMember(key, value);
        return Objects.equals(Boolean.TRUE, member);
    }

    @Override
    public void deleteHashKey(String key, Object... hKeys) {
        redisTemplate.opsForHash().delete(key, hKeys);
    }

    @Override
    public void setSetValue(String key, String... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public void setBitmap(String key, Long ops, Boolean value) {
        redisTemplate.opsForValue().setBit(key, ops, value);
    }

    @Override
    public boolean getBitmap(String key, Long ops) {
        Boolean bit = redisTemplate.opsForValue().getBit(key, ops);
        return Objects.equals(Boolean.TRUE, bit);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> cls) {
        String value = this.getValue(key);
        if (value == null) {
            return Collections.emptyList();
        }
        return jsonService.fromJsonList(value, cls);
    }

    @Override
    public Long getBitmapOffset(String key, Long offset, int length) {
        List<Long> longList = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.signed(length)).valueAt(offset));
        return CollectionUtils.isEmpty(longList) ? null : longList.get(0);
    }

    @Override
    public Map<String, Boolean> batchHasHashKey(List<String> keys, String hKey) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }
        List<Object> results = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            byte[] hKeyBytes = hKey.getBytes(StandardCharsets.UTF_8);
            for (String key : keys) {
                connection.hashCommands().hExists(key.getBytes(StandardCharsets.UTF_8), hKeyBytes);
            }
            return null;
        });
        Map<String, Boolean> resultMap = new HashMap<>(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            resultMap.put(keys.get(i), Boolean.TRUE.equals(results.get(i)));
        }
        return resultMap;
    }
    
    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    @Override
    public long increment(String key) {
        Long increment = redisTemplate.opsForValue().increment(key);
        return increment != null ? increment : 0L;
    }
    
    @Override
    public void expire(String key, long expire, TimeUnit unit) {
        redisTemplate.expire(key, expire, unit);
    }
    
    @Override
    public boolean putIfAbsent(String key, String value, long expire, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, expire, unit));
    }
}
