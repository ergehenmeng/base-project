package com.eghm.service.cache.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.exception.ParameterException;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.SystemConstant;
import com.eghm.model.ext.AsyncResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
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
public class CacheServiceImpl implements CacheService {

    private StringRedisTemplate redisTemplate;

    private ValueOperations<String, String> opsForValue;

    private ListOperations<String, String> opsForList;

    private HashOperations<String, String, String> opsForHash;

    private SysConfigApi sysConfigApi;

    private JsonService jsonService;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    /**
     * 默认过期数据
     */
    private static final long DEFAULT_EXPIRE = 30;

    /**
     * 互斥等待时间
     */
    private static final long MUTEX_EXPIRE = 10;

    /**
     * bitmap最大位
     */
    private static final int BITMAP = 64;

    public CacheServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
        this.opsForValue = stringRedisTemplate.opsForValue();
        this.opsForList = stringRedisTemplate.opsForList();
        this.opsForHash = stringRedisTemplate.opsForHash();
    }

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
        if (SystemConstant.CACHE_PLACE_HOLDER.equals(value)) {
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
        T result = this.mutexLock(key, supplier);
        if (result != null) {
            this.setValue(key, result, sysConfigApi.getLong(ConfigConstant.CACHE_EXPIRE, DEFAULT_EXPIRE));
        } else {
            // 数据库也没有查询到,填充默认值
            this.setValue(key, SystemConstant.CACHE_PLACE_HOLDER, sysConfigApi.getLong(ConfigConstant.NULL_EXPIRE, DEFAULT_EXPIRE));
        }
        return result;
    }

    /**
     * 从提供商处获取数据信息(一般为数据库),采用互斥锁进行获取,互斥锁默认过期时间10秒,即数据库查询时间不能超过10秒,否则可能出现缓存击穿
     * @param key 缓存key
     * @param supplier 数据提供方
     * @param <T> 数据结果类型
     * @return 数据结果
     */
    private <T> T mutexLock(String key, Supplier<T> supplier) {
        Boolean absent = opsForValue.setIfAbsent(CacheConstant.MUTEX_LOCK + key, SystemConstant.CACHE_PLACE_HOLDER, MUTEX_EXPIRE, TimeUnit.SECONDS);
        if (absent != null && absent) {
            T result = supplier.get();
            redisTemplate.delete(CacheConstant.MUTEX_LOCK + key);
            return result;
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            log.error("缓存互斥锁中断异常 key:[{}]", key, e);
            Thread.currentThread().interrupt();
        }
        // 递归获取
        return this.doSupplier(key, supplier);
    }

    @Override
    public void setValue(String key, Object value, long expire) {
        if (value instanceof String) {
            opsForValue.set(key, (String)value, expire, TimeUnit.SECONDS);
        } else {
            opsForValue.set(key, jsonService.toJson(value), expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean exist(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        Boolean absent = opsForValue.setIfAbsent(key, value);
        return Boolean.TRUE.equals(absent);
    }

    @Override
    public boolean setIfAbsent(String key, String value, long expire) {
        boolean absent = this.setIfAbsent(key, value);
        if (absent) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return absent;
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
        return opsForValue.get(key);
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
        Long size = opsForList.size(key);
        String leftPop;
        if (size == null || size < maxLimit || (leftPop = opsForList.leftPop(key)) == null) {
            opsForList.rightPush(key, String.valueOf(System.currentTimeMillis()));
            return false;
        }
        // 如果刚好此时,在maxTtl时间内的第一次存储的数据过期了,依旧返回true,不做毫秒值等判断
        if (DateUtil.currentSeconds() - Long.parseLong(leftPop) < maxLimit) {
            return true;
        }
        opsForList.rightPush(key, String.valueOf(DateUtil.currentSeconds()));
        // 相当于集合中只保留最多maxLimit个元素
        opsForList.trim(key, size - maxLimit, size - 1);
        return false;
    }

    @Override
    public void setHashValue(String key, String hKey, String hValue) {
        opsForHash.put(key, hKey, hValue);
    }

    @Override
    public void setHashValue(String key, long expire, String hKey, String hValue) {
        opsForHash.put(key, hKey, hValue);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public String getHashValue(String key, String hKey) {
        return opsForHash.get(key, hKey);
    }

    @Override
    public void deleteHashKey(String key, Object... hKeys) {
        opsForHash.delete(key, hKeys);
    }

    @Override
    public void setBitmap(String key, Long ops, Boolean value) {
        opsForValue.setBit(key, ops, value);
    }

    @Override
    public Boolean getBitmap(String key, Long ops) {
        return opsForValue.getBit(key, ops);
    }

    @Override
    public boolean getBitmapSuccession(String key, Long end, Integer succession) {
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
        int firstOffset = succession % 64;
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
                offset += 64;
            }
        }

        List<Long> bitField = opsForValue.bitField(key, subCommands);
        if (CollUtil.isEmpty(bitField)) {
            return false;
        }
        for (int i = 0,size = bitField.size(); i < size; i++) {
            int maxSize = (i == 0) ? firstOffset : 64;
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
    public boolean getSimpleBitmapSuccession(String key, Long end, Integer succession) {
        // end确认了该bitmap的位的总长度
        if (end < succession) {
            return false;
        }
        if (succession > 64) {
            log.error("bitmap位数不能超过64 succession:[{}]", succession);
            throw new ParameterException(ErrorCode.DATA_ERROR);
        }
        List<Long> longList = opsForValue.bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.INT_64).valueAt(end - succession));
        if (CollUtil.isEmpty(longList)) {
            return false;
        }
        Long longBit = longList.get(0);
        for (int j = 0; j < succession; j++) {
            if (longBit >> 1 << 1 == longBit) {
                return false;
            }
            longBit >>= 1;
        }
        return true;
    }

    /**
     * 计算需要多少个命令才能执行完, bitField采用有符号long数据时,最大支持64为数据
     */
    private int getCommands(Integer succession) {
        if (succession / 64 == 0) {
            return succession / 64;
        }
        return succession / 64 + 1;
    }

    @Override
    public Long getBitmapCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes(StandardCharsets.UTF_8)));
    }
}
