package com.eghm.cache.impl;

import com.eghm.cache.CacheService;
import com.eghm.common.JsonService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.LockConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.lock.RedisLock;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.eghm.constants.CacheConstant.DEFAULT_EXPIRE;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 *
 * @author 二哥很猛
 * @since 2018/11/21 16:28
 */
@Service("cacheService")
@Slf4j
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisLock redisLock;

    private final JsonService jsonService;

    private final SysConfigApi sysConfigApi;

    private final StringRedisTemplate redisTemplate;

    @Override
    public void setValue(String key, Object value) {
        this.setValue(key, value, sysConfigApi.getLong(ConfigConstant.CACHE_EXPIRE));
    }

    @Override
    public <T> T getValue(String key, TypeReference<T> type, Supplier<T> supplier) {
        String value;
        try {
            value = this.getValue(key);
        } catch (RuntimeException e) {
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
            this.setValue(key, result, sysConfigApi.getLong(ConfigConstant.CACHE_EXPIRE, DEFAULT_EXPIRE));
        } else {
            // 数据库也没有查询到,填充默认值
            this.setValue(key, CacheConstant.PLACE_HOLDER, sysConfigApi.getLong(ConfigConstant.NULL_EXPIRE, DEFAULT_EXPIRE));
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
        if (value instanceof String) {
            redisTemplate.opsForValue().set(key, (String) value, expire, unit);
        } else {
            redisTemplate.opsForValue().set(key, jsonService.toJson(value), expire, unit);
        }
    }

    @Override
    public boolean exist(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value);
        return Boolean.TRUE.equals(absent);
    }

    @Override
    public void setValue(String key, Object value, Date expireTime) {
        this.setValue(key, value);
        redisTemplate.expireAt(key, expireTime);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
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
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        return expire != null ? expire : -1;
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
        redisTemplate.expire(key, CommonConstant.LIMIT_MAX_EXPIRE, TimeUnit.MILLISECONDS);
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
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public boolean hasSetKey(String key, Object value) {
        Boolean member = redisTemplate.opsForSet().isMember(key, value);
        return Boolean.TRUE.equals(member);
    }

    @Override
    public long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public void deleteHashKey(String key, Object... hKeys) {
        redisTemplate.opsForHash().delete(key, hKeys);
    }

    @Override
    public void setBitmap(String key, Long ops, Boolean value) {
        redisTemplate.opsForValue().setBit(key, ops, value);
    }

    @Override
    public void setSetValue(String key, String... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public boolean getBitmap(String key, Long ops) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, ops));
    }

    @Override
    public Long getBitmapOffset(String key, Long offset) {
        List<Long> longList = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.UINT_32).valueAt(offset));
        return CollectionUtils.isEmpty(longList) ? null : longList.get(0);
    }

    @Override
    public Long getBitmapOffset(String key, Long offset, int length) {
        List<Long> longList = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.signed(length)).valueAt(offset));
        return CollectionUtils.isEmpty(longList) ? null : longList.get(0);
    }

    @Override
    public void setSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public void setSetIncrement(String key, String value, double score) {
        redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> rangeWithScore(String key, Integer limit) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, limit);
    }

}
