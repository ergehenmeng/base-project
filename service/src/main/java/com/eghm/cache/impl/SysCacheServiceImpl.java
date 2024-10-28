package com.eghm.cache.impl;

import com.eghm.cache.ClearCacheService;
import com.eghm.cache.SysCacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.mapper.SysCacheMapper;
import com.eghm.model.SysCache;
import com.eghm.service.sys.BlackRosterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统缓存清理服务类(所有系统级缓存清理的入口)
 *
 * @author 二哥很猛
 * @since 2019/1/14 14:34
 */
@Service("sysCacheService")
@Slf4j
@AllArgsConstructor
public class SysCacheServiceImpl implements SysCacheService {

    private final SysCacheMapper sysCacheMapper;

    private final ClearCacheService clearCacheService;

    private final BlackRosterService blackRosterService;

    @Override
    public void clearCache(List<String> cacheNames) {
        for (String cacheName : cacheNames) {
            boolean cache = this.clearCache(cacheName);
            this.updateCacheState(cacheName, cache);
        }
    }

    @Override
    public List<SysCache> getList() {
        return sysCacheMapper.selectList(null);
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
                case CacheConstant.SYS_CONFIG:
                    clearCacheService.clearSysConfig();
                    break;
                case CacheConstant.SYS_DICT:
                    clearCacheService.clearSysDict();
                    break;
                case CacheConstant.SENSITIVE_WORD:
                    clearCacheService.clearSensitiveWord();
                    break;
                case CacheConstant.BANNER:
                    clearCacheService.clearBanner();
                    break;
                case CacheConstant.SYS_NOTICE:
                    clearCacheService.clearNotice();
                    break;
                case CacheConstant.EMAIL_TEMPLATE:
                    clearCacheService.clearFreemarkerTemplate();
                    clearCacheService.clearEmailTemplate();
                    break;
                case CacheConstant.IN_MAIL_TEMPLATE:
                    clearCacheService.clearInMailTemplate();
                    break;
                case CacheConstant.SYS_AREA:
                    clearCacheService.clearSysArea();
                    break;
                case CacheConstant.AUTH_CONFIG:
                    clearCacheService.clearAuthConfig();
                    break;
                case CacheConstant.BLACK_ROSTER:
                    blackRosterService.reloadBlackRoster();
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
        SysCache cache = new SysCache();
        cache.setCacheName(cacheName);
        cache.setUpdateTime(LocalDateTime.now());
        if (state) {
            cache.setState(1);
        } else {
            cache.setState(2);
        }
        sysCacheMapper.updateCache(cache);
    }
}
