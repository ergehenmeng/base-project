package com.eghm.platform.iam.service.impl;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.web.utility.IpRegionUtil;
import com.eghm.platform.iam.entity.SysUser;
import com.eghm.platform.iam.event.LoginSecurityEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录缓存管理器
 * 统一管理登录相关的缓存操作, 委托LoginSecurityService实现账号/IP锁定,
 * 委托LoginGeoService实现异地登录检测, 并负责发布登录安全事件
 *
 * @author 二哥很猛
 * @since 2025/1/28
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginCacheManager {
    
    private final CacheService cacheService;
    
    private final LoginGeoService loginGeoService;
    
    private final ApplicationEventPublisher eventPublisher;
    
    private final LoginSecurityService loginSecurityService;
    
    private final ApplicationProperties applicationProperties;
    
    /**
     * 检查账号是否已被锁定
     *
     * @param account 账号(用户名或手机号)
     * @return true:已锁定 false:未锁定
     */
    public boolean isLocked(String account) {
        try {
            loginSecurityService.checkAccountNotLocked(account);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
    
    /**
     * 记录登录失败: 同时递增账号错误计数和IP错误计数
     * 当达到阈值时发布账号锁定事件或IP锁定事件
     *
     * @param account 账号(用户名或手机号)
     * @param ip      客户端IP地址
     */
    public void incrementLoginError(String account, String ip) {
        loginSecurityService.recordAccountError(account, ip);
        loginSecurityService.recordIpError(ip);
        
        int errorCount = loginSecurityService.getAccountErrorCount(account);
        ApplicationProperties.LoginSecurityProperties config = applicationProperties.getManage().getLoginSecurity();
        if (errorCount >= config.getAccountMaxError()) {
            eventPublisher.publishEvent(LoginSecurityEvent.accountLocked(null, account, ip));
        }
        
        long ipErrorCount = loginSecurityService.getIpErrorCount(ip);
        if (ipErrorCount >= config.getIpMaxError()) {
            eventPublisher.publishEvent(LoginSecurityEvent.ipLocked(ip, ipErrorCount));
        }
    }
    
    /**
     * 检查用户登录地域是否发生变化(异地登录检测)
     *
     * @param userId 用户ID
     * @param ip     当前登录IP
     * @return 地域检查结果
     */
    public LoginGeoService.GeoCheckResult checkGeoChange(Long userId, String ip) {
        return loginGeoService.checkGeoChange(userId, ip);
    }
    
    /**
     * 记录用户登录IP和地域(登录成功后调用)
     *
     * @param userId 用户ID
     * @param ip     登录IP
     */
    public void recordLoginGeo(Long userId, String ip) {
        String region = IpRegionUtil.getRegion(ip);
        loginGeoService.recordLoginGeo(userId, ip, region);
    }
    
    public void clearLoginLockCache(String userName, String mobile) {
        if (userName != null) {
            loginSecurityService.clearAccountError(userName);
        }
        if (mobile != null) {
            loginSecurityService.clearAccountError(mobile);
        }
    }
    
    public void saveTotpData(String uuid, Long userId) {
        cacheService.setValue(uuid, userId, 5, TimeUnit.MINUTES);
        log.info("保存TOTP验证数据 [UUID:{}] [用户ID:{}]", uuid, userId);
    }
    
    public Long getTotpUserId(String uuid) {
        String value = cacheService.getValue(uuid);
        if (value == null) {
            log.warn("TOTP验证数据不存在或已过期 [UUID:{}]", uuid);
            return null;
        }
        return Long.parseLong(value);
    }
    
    public void clearTotpData(String uuid) {
        cacheService.delete(uuid);
    }
    
    public void clearLockScreenStatus(Long userId) {
        String lockScreenKey = CacheConstant.LOCK_SCREEN + userId;
        cacheService.delete(lockScreenKey);
    }
    
    public void clearAllLoginCache(SysUser user) {
        if (user != null) {
            this.clearLoginLockCache(user.getUserName(), user.getMobile());
            this.clearLockScreenStatus(user.getId());
        }
    }
}