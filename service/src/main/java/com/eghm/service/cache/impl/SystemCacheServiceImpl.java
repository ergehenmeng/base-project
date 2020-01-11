package com.eghm.service.cache.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.mapper.system.SystemCacheMapper;
import com.eghm.dao.model.system.SystemCache;
import com.eghm.service.cache.ClearCacheService;
import com.eghm.service.cache.SystemCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/1/14 14:34
 */
@Service("systemCacheService")
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class SystemCacheServiceImpl implements SystemCacheService {

    @Autowired
    private SystemCacheMapper systemCacheMapper;

    @Autowired
    private ClearCacheService clearCacheService;

    @Override
    public void clearCache(List<String> cacheNames) {
        for (String cacheName : cacheNames) {
            boolean cache = this.clearCache(cacheName);
            this.updateCacheState(cacheName, cache);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<SystemCache> getList() {
        return systemCacheMapper.getList();
    }

    /**
     * 根据缓存名称,清除缓存<br>
     * 注意新增缓存cacheName,需要在此处及system_cache表中配置
     *
     * @param cacheName 缓存名称
     * @return 是否成功
     */
    private boolean clearCache(String cacheName) {
        try {
            switch (cacheName) {
                case CacheConstant.SYSTEM_CONFIG:
                    clearCacheService.clearSystemConfig();
                    break;
                case CacheConstant.SYSTEM_DICT:
                    clearCacheService.clearSystemDict();
                    break;
                case CacheConstant.SMS_TEMPLATE:
                    clearCacheService.clearSmsTemplate();
                    break;
                case CacheConstant.PUSH_TEMPLATE:
                    clearCacheService.clearPushTemplate();
                    break;
                case CacheConstant.BLACK_ROSTER:
                    clearCacheService.clearBlackRoster();
                    break;
                case CacheConstant.FREEMARKER_TEMPLATE:
                    clearCacheService.clearFreemarkerTemplate();
                    break;
                default:
                    break;
            }
            return true;
        } catch (Exception e) {
            log.error("缓存清除异常,cacheName:[{}]", cacheName, e);
        }
        return false;
    }


    /**
     * 更新缓存刷新状态
     *
     * @param cacheName 缓存名称
     * @param state     状态
     */
    private void updateCacheState(String cacheName, boolean state) {
        SystemCache cache = new SystemCache();
        cache.setCacheName(cacheName);
        cache.setUpdateTime(DateUtil.getNow());
        if (state) {
            cache.setState((byte) 1);
        } else {
            cache.setState((byte) 2);
        }
        systemCacheMapper.updateCache(cache);
    }
}
