package com.eghm.lock.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.lock.RedisLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author wyb
 * @since 2023/3/26 16:26
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
    public void lockVoid(String key, long lockTime, Runnable runnable) {
        this.lock(key, 0, lockTime, () -> {
            runnable.run();
            return null;
        }, null);
    }

    @Override
    public void lockVoid(String key, long lockTime, Runnable runnable, ErrorCode errorCode) {
        this.lock(key, 0, lockTime, () -> {
            runnable.run();
            return null;
        }, () -> {
            throw new BusinessException(errorCode);
        });
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
                log.info("Redis分布式锁获取成功 [{}] [{}]", key, Thread.currentThread());
                return supplier.get();
            }
        } catch (InterruptedException e) {
            log.error("Redis锁中断异常 [{}]", key, e);
            Thread.currentThread().interrupt();
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("Redis分布式锁释放成功 [{}] [{}]", key, Thread.currentThread());
            } else {
                log.warn("锁对象非当前线程持有或者超时已过释放期 [{}] [{}] [{}] [{}]", key, waitTime, lockTime, Thread.currentThread());
            }
        }
        log.error("Redis锁对象获取失败 [{}] [{}] [{}]", key, waitTime, lockTime);
        if (failSupplier != null) {
            return failSupplier.get();
        }
        return null;
    }

    @Override
    public void lock(String key, long lockTime) {
        redissonClient.getLock(key).lock(lockTime, TimeUnit.MILLISECONDS);
    }
}
