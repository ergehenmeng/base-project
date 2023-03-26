package com.eghm.service.cache;

import java.util.function.Supplier;

/**
 * @author wyb
 * @date 2023/3/26 16:26
 */
public interface LockService {

    /**
     * 获取锁后执行指定逻辑
     * @param key key
     * @param lockTime 锁最大持续时间,单位:毫秒
     * @param supplier 获取成功后执行业务
     * @param <T> T
     * @return T
     */
    <T> T lock(String key, long lockTime, Supplier<T> supplier);

    /**
     * 加锁
     * @param key key
     * @param lockTime 锁定时间
     */
    void lock(String key, long lockTime);

    /**
     * 释放锁
     * @param key key
     */
    void unlock(String key);
}
