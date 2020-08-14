package com.eghm.service.cache.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.service.cache.ClearCacheService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Configuration configuration;

    @Autowired(required = false)
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_CONFIG, allEntries = true)
    public void clearSysConfig() {
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_DICT, allEntries = true)
    public void clearSysDict() {
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

    @Override
    public void clearFreemarkerTemplate() {
        configuration.clearTemplateCache();
    }
}
