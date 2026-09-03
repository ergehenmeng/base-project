package com.eghm.platform.iam.service.impl;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.iam.event.LoginSecurityEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录安全服务
 * 基于Redis实现分布式登录安全防护, 包括:
 * 1. 账号级别错误计数与锁定(防止密码暴力破解)
 * 2. IP级别错误计数与锁定(防止分布式撞库攻击)
 * 3. 暴力破解嫌疑提前告警(达到阈值50%时通知管理员)
 *
 * @author wyb-eghm
 * @since 2026/9/3
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginSecurityService {
    
    private final CacheService cacheService;
    
    private final ApplicationEventPublisher eventPublisher;
    
    private final ApplicationProperties applicationProperties;
    
    private static final String ACCOUNT_ERROR_KEY = "login:security:account:error:";
    
    private static final String ACCOUNT_LOCK_KEY = "login:security:account:lock:";
    
    private static final String IP_ERROR_KEY = "login:security:ip:error:";
    
    private static final String IP_LOCK_KEY = "login:security:ip:lock:";
    
    /**
     * 检查账号是否已被锁定
     * 锁定条件: 锁定标记key存在 或 错误次数达到阈值
     *
     * @param account 账号(用户名或手机号)
     * @throws BusinessException 账号已锁定时抛出 USER_ERROR_LOCK
     */
    public void checkAccountNotLocked(String account) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        if (!config.isEnabled()) {
            return;
        }
        String lockKey = ACCOUNT_LOCK_KEY + account;
        if (cacheService.hasKey(lockKey)) {
            long ttl = cacheService.getExpire(lockKey);
            log.warn("账号已被锁定 [{}] [剩余锁定时间:{}秒]", account, ttl);
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
        String errorKey = ACCOUNT_ERROR_KEY + account;
        String errorCount = cacheService.getValue(errorKey);
        if (errorCount != null && Integer.parseInt(errorCount) >= config.getAccountMaxError()) {
            lockAccount(account);
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
    }
    
    /**
     * 检查IP是否已被锁定
     * 锁定条件: 锁定标记key存在 或 错误次数达到阈值
     *
     * @param ip 客户端IP地址
     * @throws BusinessException IP已锁定时抛出 IP_LOGIN_LOCKED
     */
    public void checkIpNotLocked(String ip) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        if (!config.isEnabled() || !config.isIpLockEnabled()) {
            return;
        }
        String lockKey = IP_LOCK_KEY + ip;
        if (cacheService.hasKey(lockKey)) {
            long ttl = cacheService.getExpire(lockKey);
            log.warn("IP已被锁定 [{}] [剩余锁定时间:{}秒]", ip, ttl);
            throw new BusinessException(ErrorCode.IP_LOGIN_LOCKED);
        }
        String errorKey = IP_ERROR_KEY + ip;
        String errorCount = cacheService.getValue(errorKey);
        if (errorCount != null && Integer.parseInt(errorCount) >= config.getIpMaxError()) {
            lockIp(ip);
            throw new BusinessException(ErrorCode.IP_LOGIN_LOCKED);
        }
    }
    
    /**
     * 记录账号登录失败, 递增错误计数
     * 当错误次数达到 accountMaxError 时自动锁定账号
     *
     * @param account 账号(用户名或手机号)
     * @param ip      客户端IP地址(仅用于日志记录)
     */
    public void recordAccountError(String account, String ip) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        if (!config.isEnabled()) {
            return;
        }
        String errorKey = ACCOUNT_ERROR_KEY + account;
        long errorCount = cacheService.increment(errorKey);
        cacheService.expire(errorKey, config.getAccountLockMinutes(), TimeUnit.MINUTES);
        log.warn("账号登录失败 [{}] [当前错误次数:{}/{}] [IP:{}]",
                account, errorCount, config.getAccountMaxError(), ip);
        
        if (errorCount >= config.getAccountMaxError()) {
            lockAccount(account);
        }
    }
    
    /**
     * 记录IP登录失败, 递增错误计数
     * 当错误次数达到 ipMaxError 的50%时, 发出暴力破解嫌疑告警(仅告警不锁定)
     * 当错误次数达到 ipMaxError 时, 锁定IP
     *
     * @param ip 客户端IP地址
     */
    public void recordIpError(String ip) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        if (!config.isEnabled() || !config.isIpLockEnabled()) {
            return;
        }
        String errorKey = IP_ERROR_KEY + ip;
        long errorCount = cacheService.increment(errorKey);
        cacheService.expire(errorKey, config.getIpLockMinutes(), TimeUnit.MINUTES);
        log.warn("IP登录失败 [{}] [当前错误次数:{}/{}]", ip, errorCount, config.getIpMaxError());
        
        int suspectThreshold = Math.max(config.getIpMaxError() / 2, 1);
        if (errorCount == suspectThreshold && suspectThreshold < config.getIpMaxError()) {
            log.warn("疑似暴力破解 [IP:{}] [错误次数:{}/{}] 已达告警线", ip, errorCount, config.getIpMaxError());
            eventPublisher.publishEvent(LoginSecurityEvent.bruteForceSuspect(ip, errorCount));
        }
        
        if (errorCount >= config.getIpMaxError()) {
            lockIp(ip);
        }
    }
    
    /**
     * 获取IP的当前登录错误次数
     *
     * @param ip 客户端IP地址
     * @return 错误次数
     */
    public long getIpErrorCount(String ip) {
        String count = cacheService.getValue(IP_ERROR_KEY + ip);
        return count != null ? Long.parseLong(count) : 0;
    }
    
    /**
     * 清除账号的登录错误计数和锁定标记
     * 用于登录成功后或管理员重置密码后清除安全状态
     *
     * @param account 账号(用户名或手机号)
     */
    public void clearAccountError(String account) {
        cacheService.delete(ACCOUNT_ERROR_KEY + account);
        cacheService.delete(ACCOUNT_LOCK_KEY + account);
    }
    
    /**
     * 获取账号的当前登录错误次数
     *
     * @param account 账号(用户名或手机号)
     * @return 错误次数
     */
    public int getAccountErrorCount(String account) {
        String count = cacheService.getValue(ACCOUNT_ERROR_KEY + account);
        return count != null ? Integer.parseInt(count) : 0;
    }
    
    /**
     * 锁定账号: 设置锁定标记并清除错误计数
     *
     * @param account 账号
     */
    private void lockAccount(String account) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        String lockKey = ACCOUNT_LOCK_KEY + account;
        long lockSeconds = config.getAccountLockMinutes() * 60;
        cacheService.setValue(lockKey, 1, lockSeconds);
        cacheService.delete(ACCOUNT_ERROR_KEY + account);
        log.warn("账号已被锁定 [{}] [锁定时长:{}分钟]", account, config.getAccountLockMinutes());
    }
    
    /**
     * 锁定IP: 设置锁定标记并清除错误计数
     *
     * @param ip IP地址
     */
    private void lockIp(String ip) {
        ApplicationProperties.LoginSecurityProperties config = getConfig();
        String lockKey = IP_LOCK_KEY + ip;
        long lockSeconds = config.getIpLockMinutes() * 60;
        cacheService.setValue(lockKey, 1, lockSeconds);
        cacheService.delete(IP_ERROR_KEY + ip);
        log.warn("IP已被锁定 [{}] [锁定时长:{}分钟]", ip, config.getIpLockMinutes());
    }
    
    /**
     * 获取登录安全配置
     *
     * @return 登录安全配置属性
     */
    private ApplicationProperties.LoginSecurityProperties getConfig() {
        return applicationProperties.getManage().getLoginSecurity();
    }
}