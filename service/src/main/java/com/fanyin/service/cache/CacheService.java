package com.fanyin.service.cache;

import com.fanyin.model.ext.Async;


/**
 * @author 二哥很猛
 * @date 2018/11/21 16:19
 */
public interface CacheService {


    /**
     * 缓存对象 默认30分钟
     * @param key key
     * @param value value
     */
    void cacheValue(String key,Object value);

    /**
     * 缓存对象
     * @param key key
     * @param value value
     * @param expire 过期时间 单位秒
     */
    void cacheValue(String key,Object value,long expire);

    /**
     * 获取缓存的信息
     * @param key key
     * @return 缓存信息
     */
    Object getValue(String key);

    /**
     * 缓存任务异步结果
     * @param response 对象
     */
    void cacheAsyncResponse(Async response);

    /**
     * 获取任务异步结果
     * @param key key
     * @return 异步结果
     */
    Async getAsyncResponse(String key);

    /**
     * 获取指定key的总数 (模糊查询)
     * @param key key
     * @return 个数
     */
    int keySize(String key);
}

