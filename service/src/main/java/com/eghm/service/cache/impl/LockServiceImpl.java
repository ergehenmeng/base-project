package com.eghm.service.cache.impl;

import com.eghm.service.cache.LockService;
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
@Service("lockService")
@AllArgsConstructor
@Slf4j
public class LockServiceImpl implements LockService {

    private final RedissonClient redissonClient;

    @Override
    public <T> T lock(String key, long lockTime, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(lockTime, TimeUnit.MILLISECONDS)) {
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
        log.error("锁对象获取失败 [{}]", key);
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
