package com.eghm.platform.iam.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录安全事件
 * 用于在登录安全策略触发时异步通知管理员, 包括:
 * - ACCOUNT_LOCKED: 账号因多次登录失败被锁定
 * - IP_LOCKED: IP因多次登录失败被锁定
 * - GEO_CHANGED: 检测到异地登录
 * - BRUTE_FORCE_SUSPECT: 疑似暴力破解(达到告警线但未达到锁定线)
 *
 * @author wyb-eghm
 * @since 2026/9/3
 */
@Data
@AllArgsConstructor
public class LoginSecurityEvent {
    
    /**
     * 安全事件类型
     */
    public enum Type {
        ACCOUNT_LOCKED,
        IP_LOCKED,
        GEO_CHANGED,
        BRUTE_FORCE_SUSPECT
    }
    
    private Type type;
    
    private Long userId;
    
    private String account;
    
    private String ip;
    
    private String detail;
    
    private LocalDateTime eventTime;
    
    /**
     * 构建账号锁定事件
     *
     * @param userId  用户ID(可能为null, 锁定时可能尚未查到用户)
     * @param account 账号
     * @param ip      客户端IP
     * @return 账号锁定事件
     */
    public static LoginSecurityEvent accountLocked(Long userId, String account, String ip) {
        return new LoginSecurityEvent(Type.ACCOUNT_LOCKED, userId, account, ip, "账号因多次登录失败被锁定", LocalDateTime.now());
    }
    
    /**
     * 构建IP锁定事件
     *
     * @param ip          IP地址
     * @param errorCount  错误次数
     * @return IP锁定事件
     */
    public static LoginSecurityEvent ipLocked(String ip, long errorCount) {
        return new LoginSecurityEvent(Type.IP_LOCKED, null, null, ip, "IP因" + errorCount + "次登录失败被锁定", LocalDateTime.now());
    }
    
    /**
     * 构建异地登录事件
     *
     * @param userId       用户ID
     * @param lastIp       上次登录IP
     * @param lastRegion   上次登录地域
     * @param currentIp    当前登录IP
     * @param currentRegion 当前登录地域
     * @return 异地登录事件
     */
    public static LoginSecurityEvent geoChanged(Long userId, String lastIp, String lastRegion, String currentIp, String currentRegion) {
        return new LoginSecurityEvent(Type.GEO_CHANGED, userId, null, currentIp, "异地登录: 从[" + lastRegion + "/" + lastIp + "]变更到[" + currentRegion + "/" + currentIp + "]", LocalDateTime.now());
    }
    
    /**
     * 构建暴力破解嫌疑事件
     *
     * @param ip          IP地址
     * @param errorCount  错误次数
     * @return 暴力破解嫌疑事件
     */
    public static LoginSecurityEvent bruteForceSuspect(String ip, long errorCount) {
        return new LoginSecurityEvent(Type.BRUTE_FORCE_SUSPECT, null, null, ip, "疑似暴力破解: IP在短时间内失败" + errorCount + "次", LocalDateTime.now());
    }
}