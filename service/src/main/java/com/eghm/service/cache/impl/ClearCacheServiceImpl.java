package com.eghm.service.cache.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.service.cache.ClearCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 全局清除缓存
 *
 * @author 二哥很猛
 * @date 2019/1/14 13:52
 */
@Service("clearCacheService")
public class ClearCacheServiceImpl implements ClearCacheService {


    @Override
    @CacheEvict(cacheNames = CacheConstant.SYSTEM_CONFIG, allEntries = true)
    public void clearSystemConfig() {
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYSTEM_DICT, allEntries = true)
    public void clearSystemDict() {
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SMS_TEMPLATE, allEntries = true)
    public void clearSmsTemplate() {
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.PUSH_TEMPLATE, allEntries = true)
    public void clearPushTemplate() {
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BLACK_ROSTER, allEntries = true)
    public void clearBlackRoster() {
    }
}
