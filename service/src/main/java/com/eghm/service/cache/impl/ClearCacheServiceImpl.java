package com.eghm.service.cache.impl;

import com.eghm.constant.CacheConstant;
import com.eghm.service.cache.ClearCacheService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ClearCacheServiceImpl implements ClearCacheService {

    private Configuration configuration;

    @Autowired(required = false)
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_CONFIG, allEntries = true, beforeInvocation = true)
    public void clearSysConfig() {
        log.info("系统参数缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_DICT, allEntries = true)
    public void clearSysDict() {
        log.info("数据字典缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SMS_TEMPLATE, allEntries = true)
    public void clearSmsTemplate() {
        log.info("短信模板缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.PUSH_TEMPLATE, allEntries = true)
    public void clearPushTemplate() {
        log.info("推送模板缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BLACK_ROSTER, allEntries = true)
    public void clearBlackRoster() {
        log.info("黑名单缓存清除成功");
    }

    @Override
    public void clearFreemarkerTemplate() {
        configuration.clearTemplateCache();
    }
}
