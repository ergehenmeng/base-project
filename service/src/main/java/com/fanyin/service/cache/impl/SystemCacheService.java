package com.fanyin.service.cache.impl;

import com.fanyin.dao.model.system.SystemCache;

import java.util.List;

/**
 * 系统缓存管理
 * @author 二哥很猛
 * @date 2019/1/14 14:32
 */
public interface SystemCacheService {


    /**
     * 根据缓存名称清除缓存
     * 具体查看 {@link com.fanyin.common.constant.RedisConstant}
     * @param cacheNames 缓存名称
     */
    void clearCache(List<String> cacheNames);

    /**
     * 获取所有的缓存信息
     * @return 列表
     */
    List<SystemCache> getList();
}

