package com.eghm.platform.iam.service.impl;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.platform.iam.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.eghm.foundation.web.utility.CacheUtil.LOGIN_LOCK_CACHE;
import static com.eghm.foundation.web.utility.CacheUtil.TOTP_CACHE;

/**
 * 登录缓存管理器
 * 统一管理登录相关的缓存操作，包括登录锁定、TOTP验证、锁屏状态等
 *
 * @author 二哥很猛
 * @since 2025/1/28
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginCacheManager {
    
    private final CacheService cacheService;
    
    /**
     * 检查用户是否被锁定
     *
     * @param account 账号（用户名或手机号）
     * @return 是否被锁定
     */
    public boolean isLocked(String account) {
        Integer errorCount = LOGIN_LOCK_CACHE.getIfPresent(account);
        boolean locked = errorCount != null && errorCount > CommonConstant.MAX_ERROR_NUM;
        if (locked) {
            log.warn("用户账号已被锁定 [{}] [错误次数:{}]", account, errorCount);
        }
        return locked;
    }
    
    /**
     * 增加登录错误次数
     *
     * @param account 账号（用户名或手机号）
     */
    public void incrementLoginError(String account) {
        int errorCount = LOGIN_LOCK_CACHE.asMap().merge(account, 1, Integer::sum);
        log.warn("用户登录错误次数增加 [{}] [当前错误次数:{}]", account, errorCount);
        if (errorCount > CommonConstant.MAX_ERROR_NUM) {
            log.warn("用户账号达到最大错误次数，已被锁定 [{}] [错误次数:{}]", account, errorCount);
        }
    }
 
    /**
     * 清除指定账号的登录锁定缓存
     *
     * @param userName 用户名
     * @param mobile   手机号
     */
    public void clearLoginLockCache(String userName, String mobile) {
        if (userName != null) {
            LOGIN_LOCK_CACHE.invalidate(userName);
        }
        if (mobile != null) {
            LOGIN_LOCK_CACHE.invalidate(mobile);
        }
    }
    
    /**
     * 获取用户登录错误次数
     *
     * @param account 账号（用户名或手机号）
     * @return 错误次数，如果不存在返回0
     */
    public int getLoginErrorCount(String account) {
        Integer errorCount = LOGIN_LOCK_CACHE.getIfPresent(account);
        return errorCount != null ? errorCount : 0;
    }
    
    /**
     * 保存TOTP验证临时数据
     *
     * @param uuid    唯一标识
     * @param userId  用户ID
     */
    public void saveTotpData(String uuid, Long userId) {
        TOTP_CACHE.put(uuid, userId);
        log.info("保存TOTP验证数据 [UUID:{}] [用户ID:{}]", uuid, userId);
    }
    
    /**
     * 获取TOTP验证的用户ID
     *
     * @param uuid 唯一标识
     * @return 用户ID，如果不存在或已过期返回null
     */
    public Long getTotpUserId(String uuid) {
        Long userId = TOTP_CACHE.getIfPresent(uuid);
        if (userId == null) {
            log.warn("TOTP验证数据不存在或已过期 [UUID:{}]", uuid);
        }
        return userId;
    }
    
    /**
     * 清除TOTP验证数据
     *
     * @param uuid 唯一标识
     */
    public void clearTotpData(String uuid) {
        TOTP_CACHE.invalidate(uuid);
        log.info("清除TOTP验证数据 [UUID:{}]", uuid);
    }
    
    /**
     * 清除用户锁屏状态
     *
     * @param userId 用户ID
     */
    public void clearLockScreenStatus(Long userId) {
        String lockScreenKey = CacheConstant.LOCK_SCREEN + userId;
        cacheService.delete(lockScreenKey);
        log.info("清除用户锁屏状态 [用户ID:{}]", userId);
    }
    
    /**
     * 清除用户所有登录相关缓存
     * 包括：登录锁定缓存、锁屏状态缓存
     *
     * @param user 用户信息
     */
    public void clearAllLoginCache(SysUser user) {
        if (user != null) {
            this.clearLoginLockCache(user.getUserName(), user.getMobile());
            this.clearLockScreenStatus(user.getId());
            log.info("清除用户所有登录相关缓存 [用户ID:{}] [用户名:{}] [手机号:{}]", user.getId(), user.getUserName(), user.getMobile());
        }
    }
    
}
