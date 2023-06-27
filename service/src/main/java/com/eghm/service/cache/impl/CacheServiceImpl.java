package com.eghm.service.cache.impl;

import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.AsyncResponse;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.eghm.service.cache.CacheService;
import com.eghm.service.cache.RedisLock;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 用于缓存数据信息,不涉及数据查询数据缓存
 *
 * @author 二哥很猛
 * @date 2018/11/21 16:28
 */
@Service("cacheService")
@Slf4j
@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final StringRedisTemplate redisTemplate;

    private final SysConfigApi sysConfigApi;

    private final JsonService jsonService;

    private final RedisLock redisLock;

    /**
     * 默认过期数据 30s
     */
    private static final long DEFAULT_EXPIRE = 30_000;

    /**
     * 互斥等待时间 10s
     */
    private static final long MUTEX_EXPIRE = 10_000;

    /**
     * bitmap最大位
     */
    private static final int BITMAP = 64;
    
    @Override
    public void setValue(String key, Object value) {
        this.setValue(key, value, sysConfigApi.getLong(ConfigConstant.CACHE_EXPIRE));
    }

    @Override
    public void setPersistent(String key, Object value) {
        if (value instanceof String) {
            redisTemplate.opsForValue().set(key, (String) value);
        } else {
            redisTemplate.opsForValue().set(key, jsonService.toJson(value));
        }
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
     * @param key  缓存的key
     * @param supplier 会到函数
     * @param <T> 结果类型
     * @return 结果信息
     */
    private <T> T doSupplier(String key, Supplier<T> supplier) {
        T result = redisLock.lock(CacheConstant.MUTEX_LOCK + key, MUTEX_EXPIRE, supplier);
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
        if (value instanceof String) {
            redisTemplate.opsForValue().set(key, (String)value, expire, TimeUnit.MILLISECONDS);
        } else {
            redisTemplate.opsForValue().set(key, jsonService.toJson(value), expire, TimeUnit.MILLISECONDS);
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
    public boolean setIfAbsent(String key, String value, long expire) {
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.MILLISECONDS);
        return Boolean.TRUE.equals(absent);
    }

    @Override
    public void setValue(String key, Object value, Date expireTime) {
        this.setValue(key, value);
        redisTemplate.expireAt(key, expireTime);
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
    public int keySize(String key) {
        Set<String> keys = redisTemplate.keys(key);
        if (!CollectionUtils.isEmpty(keys)) {
            return keys.size();
        }
        return 0;
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
    public boolean hasHashKey(String key, String hKey) {
        Boolean hasKey = redisTemplate.opsForHash().hasKey(key, hKey);
        return Boolean.TRUE.equals(hasKey);
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
    public boolean getBitmap(String key, Long ops) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, ops));
    }

    @Override
    public boolean checkSerialBoost(String key, Long end, Integer succession) {
        // end确认了该bitmap的位的总长度
        if (end < succession) {
            return false;
        }
        // 并非从头开始查找连续的节点
        long start = end - succession;
        // bigField key [GET type offset] [SET type offset value] 支持多命令同时查询,返回数组
        // type: i8:表示取8位有符号 u16:表示无符号取16位 offset:表示从哪一位开始取
        BitFieldSubCommands subCommands = BitFieldSubCommands.create();
        // succession如果比较大的话 需要执行多次命令才能查询想要的结果
        int commands = this.getCommands(succession);
        int firstOffset = succession % BITMAP;
        long offset = start;
        // 11111111 11111111 11111011 11111111 111111111 11111111 11111111 11111111 11111111 11111111 80位的总长度
        // 判断最近70天是否连续, 则从第10位开始,第一次取6位(同时在判断时也只判断低位的6位) 第二次取64位
        for (long i = start; i < commands; i++ ) {
            // 第一个命令,如果直接设置64,在总长度时不变时,会导致最后一个数不准确
            if (i == start && firstOffset > 0) {
                subCommands = subCommands.get(BitFieldSubCommands.BitFieldType.signed(firstOffset)).valueAt(offset);
                offset += firstOffset;
            } else {
                subCommands = subCommands.get(BitFieldSubCommands.BitFieldType.INT_64).valueAt(offset);
                offset += BITMAP;
            }
        }

        List<Long> bitField = redisTemplate.opsForValue().bitField(key, subCommands);
        if (CollectionUtils.isEmpty(bitField)) {
            return false;
        }
        for (int i = 0,size = bitField.size(); i < size; i++) {
            int maxSize = (i == 0) ? firstOffset : BITMAP;
            long longBit = bitField.get(i);
            for (int j = 0; j < maxSize; j++) {
                if (longBit >> 1 << 1 == longBit) {
                    return false;
                }
                longBit >>= 1;
            }
        }
        return true;
    }

    @Override
    public boolean checkSerial(String key, Long end, Integer succession) {
        Long longBit = this.getBitmapLong(key, end, succession);
        if (longBit == null) {
            return false;
        }
        for (int j = 0; j < succession; j++) {
            if (longBit >> 1 << 1 == longBit) {
                return false;
            }
            longBit >>= 1;
        }
        return true;
    }

    @Override
    public Long getBitmap64(String key, Long end) {
        return this.getBitmapLong(key, end, BITMAP);
    }

    private Long getBitmapLong(String key, Long end, int maxLength) {
        if (maxLength > BITMAP) {
            log.error("bitmap位数过大 [{}]", maxLength);
            throw new ParameterException(ErrorCode.DATA_ERROR);
        }
        long startIndex = (end - maxLength) > 0 ? end - maxLength : 0;
        List<Long> longList = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.INT_64).valueAt(startIndex));
        return CollectionUtils.isEmpty(longList) ? null : longList.get(0);
    }

    @Override
    public Long getBitmapCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 计算需要多少个命令才能执行完, bitField采用有符号long数据时,最大支持64为数据
     */
    private int getCommands(Integer succession) {
        if (succession / BITMAP == 0) {
            return succession / BITMAP;
        }
        return succession / BITMAP + 1;
    }
}
