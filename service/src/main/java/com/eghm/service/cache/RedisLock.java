package com.eghm.service.cache;

import com.eghm.enums.ErrorCode;

import java.util.function.Supplier;

/**
 * @author wyb
 * @date 2023/3/26 16:26
 */
public interface RedisLock {

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
     * 获取锁后执行指定逻辑
     * @param key key
     * @param waitTime 最大等待时间, 单位:毫秒
     * @param lockTime 锁最大持续时间,单位:毫秒
     * @param supplier 获取成功后执行业务
     * @param <T> T
     * @return T
     */
    <T> T lock(String key, long waitTime, long lockTime, Supplier<T> supplier);

    /**
     * 获取锁后执行指定逻辑,如果获取失败则执行失败策略
     * @param key key
     * @param lockTime 锁最大持续时间,单位:毫秒
     * @param supplier 获取成功后执行业务
     * @param failSupplier 失败的业务策略
     * @param <T> T
     * @return T
     */
    <T> T lock(String key, long lockTime, Supplier<T> supplier, Supplier<T> failSupplier);

    /**
     * 获取锁后执行指定逻辑,如果获取失败则执行失败策略
     * @param key key
     * @param lockTime 锁最大持续时间,单位:毫秒
     * @param supplier 获取成功后执行业务
     * @param error 获取失败时直接抛出指定的异常
     * @param <T> T
     * @return T
     */
    <T> T lock(String key, long lockTime, Supplier<T> supplier, ErrorCode error);

    /**
     * 获取锁后执行指定逻辑
     * @param key key
     * @param waitTime 最大等待时间, 单位:毫秒
     * @param lockTime 锁最大持续时间,单位:毫秒
     * @param supplier 获取成功后执行业务
     * @param failSupplier 获取锁失败后执行的业务
     * @param <T> T
     * @return T
     */
    <T> T lock(String key, long waitTime, long lockTime, Supplier<T> supplier, Supplier<T> failSupplier);

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
