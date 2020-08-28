package com.eghm.service.cache;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.model.system.SysCache;

import java.util.List;

/**
 * 系统缓存管理
 *
 * @author 二哥很猛
 * @date 2019/1/14 14:32
 */
public interface SysCacheService {


    /**
     * 根据缓存名称清除缓存
     * 具体查看 {@link CacheConstant}
     *
     * @param cacheNames 缓存名称
     */
    void clearCache(List<String> cacheNames);

    /**
     * 获取所有的缓存信息
     *
     * @return 列表
     */
    List<SysCache> getList();
}

