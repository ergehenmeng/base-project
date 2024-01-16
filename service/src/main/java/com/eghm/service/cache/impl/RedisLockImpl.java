package com.eghm.service.cache.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.service.cache.RedisLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author wyb
 * @date 2023/3/26 16:26
 */
@Slf4j
@Service("redisLock")
@AllArgsConstructor
public class RedisLockImpl implements RedisLock {

    private final RedissonClient redissonClient;

    @Override
    public <T> T lock(String key, long lockTime, Supplier<T> supplier) {
        return this.lock(key, 0, lockTime, supplier, null);
    }

    @Override
    public <T> T lock(String key, long waitTime, long lockTime, Supplier<T> supplier) {
        return this.lock(key, waitTime, lockTime, supplier, null);
    }

    @Override
    public <T> T lock(String key, long lockTime, Supplier<T> supplier, Supplier<T> failSupplier) {
        return this.lock(key, 0, lockTime, supplier, failSupplier);
    }

    @Override
    public <T> T lock(String key, long lockTime, Supplier<T> supplier, ErrorCode error) {
        return this.lock(key, 0, lockTime, supplier, () -> {
            throw new BusinessException(error);
        });
    }

    @Override
    public <T> T lock(String key, long waitTime, long lockTime, Supplier<T> supplier, Supplier<T> failSupplier) {
        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(waitTime, lockTime, TimeUnit.MILLISECONDS)) {
                try {
                    return supplier.get();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("锁中断异常 [{}]", key, e);
            Thread.currentThread().interrupt();
        }
        log.error("锁对象获取失败 [{}] [{}] [{}]", key, waitTime, lockTime);
        if (failSupplier != null) {
            return failSupplier.get();
        }
        return null;
    }

    @Override
    public void lock(String key, long lockTime) {
        redissonClient.getLock(key).lock(lockTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void unlock(String key) {
        RLock rLock = redissonClient.getLock(key);
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }
}
