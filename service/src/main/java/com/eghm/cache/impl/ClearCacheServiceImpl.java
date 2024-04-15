package com.eghm.cache.impl;

import com.eghm.constant.CacheConstant;
import com.eghm.cache.ClearCacheService;
import com.eghm.service.common.SensitiveWordService;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 全局清除缓存
 *
 * @author 二哥很猛
 * @since 2019/1/14 13:52
 */
@Slf4j
@Service("clearCacheService")
@RequiredArgsConstructor
public class ClearCacheServiceImpl implements ClearCacheService {

    private final SensitiveWordService sensitiveWordService;

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
    public void clearSensitiveWord() {
        sensitiveWordService.reloadLexicon();
        log.info("敏感词缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.PAY_CONFIG, allEntries = true)
    public void clearPayConfig() {
        log.info("支付配置缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.BANNER, allEntries = true)
    public void clearBanner() {
        log.info("banner缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.SYS_NOTICE, allEntries = true)
    public void clearNotice() {
        log.info("公告缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = {CacheConstant.SYS_AREA_ID, CacheConstant.SYS_AREA_ID, CacheConstant.SYS_AREA_PID}, allEntries = true)
    public void clearSysArea() {
        log.info("省市区缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.PUSH_TEMPLATE, allEntries = true)
    public void clearPushTemplate() {
        log.info("推送模板缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.ITEM_TAG, allEntries = true)
    public void clearItemTag() {
        log.info("零售标签缓存清除成功");
    }

    @Override
    @CacheEvict(cacheNames = CacheConstant.AUTH_CONFIG, allEntries = true)
    public void clearAuthConfig() {
        log.info("第三方授权配置缓存清除成功");
    }

    @Override
    public void clearFreemarkerTemplate() {
        if (configuration != null) {
            configuration.clearTemplateCache();
        }
    }
}
