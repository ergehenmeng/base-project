package com.fanyin.service.cache;

import com.fanyin.ext.Async;
import com.fanyin.ext.AccessToken;


/**
 * @author 二哥很猛
 * @date 2018/11/21 16:19
 */
public interface CacheService {

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
     * 根据accessKey查找token
     * @param accessKey accessKey
     * @return token
     */
    AccessToken getAccessToken(String accessKey);

    /**
     * 保存token 30分钟超时时间
     * @param token token对象
     */
    void cacheAccessToken(AccessToken token);
}

