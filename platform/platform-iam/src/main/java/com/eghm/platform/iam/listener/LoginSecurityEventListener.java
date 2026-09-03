package com.eghm.platform.iam.listener;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.platform.iam.event.LoginSecurityEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 登录安全事件监听器
 * 异步处理登录安全事件(账号锁定/IP锁定/异地登录/暴力破解), 并根据配置决定是否发送告警通知
 *
 * @author eghm
 * @since 2026/4/12
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginSecurityEventListener {
    
    private final AlarmService alarmService;
    
    private final ApplicationProperties applicationProperties;
    
    /**
     * 异步处理登录安全事件
     * 根据事件类型和配置开关决定是否发送告警通知:
     * - ACCOUNT_LOCKED:  notifyOnLock=true 时通知
     * - IP_LOCKED:        notifyOnLock=true 时通知
     * - GEO_CHANGED:      notifyOnGeoChange=true 时通知
     * - BRUTE_FORCE_SUSPECT: 始终通知
     * 告警消息中包含userId/account/ip等关键信息, 便于管理员快速定位问题
     *
     * @param event 登录安全事件
     */
    @Async
    @EventListener
    public void onLoginSecurityEvent(LoginSecurityEvent event) {
        ApplicationProperties.LoginSecurityProperties config = applicationProperties.getManage().getLoginSecurity();
        log.warn("收到登录安全事件 [type:{}] [userId:{}] [account:{}] [ip:{}] [detail:{}]",
                event.getType(), event.getUserId(), event.getAccount(), event.getIp(), event.getDetail());
        
        String msgType = resolveMsgType(event, config);
        if (msgType == null) {
            log.info("登录安全事件通知已关闭,跳过发送 [type:{}]", event.getType());
            return;
        }
        String content = buildAlarmContent(msgType, event);
        alarmService.sendMsg(content);
    }
    
    /**
     * 根据事件类型和配置开关解析告警类型, 返回null表示该类型通知已关闭
     *
     * @param event  登录安全事件
     * @param config 登录安全配置
     * @return 告警类型描述, null表示不发送通知
     */
    private String resolveMsgType(LoginSecurityEvent event, ApplicationProperties.LoginSecurityProperties config) {
        return switch (event.getType()) {
            case ACCOUNT_LOCKED -> config.isNotifyOnLock() ? "账号被锁定" : null;
            case IP_LOCKED -> config.isNotifyOnLock() ? "登录安全告警" : null;
            case GEO_CHANGED -> config.isNotifyOnGeoChange() ? "异地登录告警" : null;
            case BRUTE_FORCE_SUSPECT -> "暴力破解告警";
        };
    }
    
    /**
     * 构建告警通知内容, 包含事件的关键定位信息(userId/account/ip)
     *
     * @param msgType 告警类型
     * @param event   登录安全事件
     * @return 完整告警内容
     */
    private String buildAlarmContent(String msgType, LoginSecurityEvent event) {
        StringBuilder sb = new StringBuilder(msgType).append("：").append(event.getDetail());
        if (event.getUserId() != null) {
            sb.append(" [用户ID:").append(event.getUserId()).append("]");
        }
        if (event.getAccount() != null) {
            sb.append(" [账号:").append(event.getAccount()).append("]");
        }
        if (event.getIp() != null) {
            sb.append(" [IP:").append(event.getIp()).append("]");
        }
        return sb.toString();
    }
}