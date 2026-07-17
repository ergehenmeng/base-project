package com.eghm.app.webapp.listener;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.constants.QueueConstant;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.messaging.mq.listener.AbstractListenerHandler;
import com.eghm.member.account.dto.LoginRecord;
import com.eghm.member.account.service.LoginService;
import com.eghm.platform.audit.entity.WebappLog;
import com.eghm.platform.audit.service.WebappLogService;
import com.eghm.platform.config.service.SensitiveWordReloader;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Component
@Slf4j
public class WebappListenerHandler extends AbstractListenerHandler {

    private final LoginService loginService;

    private final WebappLogService webappLogService;

    private final SensitiveWordReloader sensitiveWordReloader;

    public WebappListenerHandler(JsonService jsonService, AlarmService alarmService, LoginService loginService, CacheService cacheService, WebappLogService webappLogService, SensitiveWordReloader sensitiveWordReloader) {
        super(jsonService, cacheService, alarmService);
        this.loginService = loginService;
        this.webappLogService = webappLogService;
        this.sensitiveWordReloader = sensitiveWordReloader;
    }

    /**
     * 移动端操作日志
     */
    @RabbitListener(queues = QueueConstant.WEBAPP_LOG_QUEUE)
    public void webappLog(WebappLog webappLog, Message message, Channel channel) throws IOException {
        processMessageAck(webappLog, message, channel, webappLogService::insertWebappLog);
    }

    /**
     * 移动端登陆日志
     */
    @RabbitListener(queues = QueueConstant.LOGIN_LOG_QUEUE)
    public void loginLog(LoginRecord loginRecord, Message message, Channel channel) throws IOException {
        processMessageAck(loginRecord, message, channel, loginService::insertLoginLog);
    }

    /**
     * 敏感词同步
     */
    @RabbitListener(queues = QueueConstant.SENSITIVE_SYNC_QUEUE)
    public void sensitiveSync(String appName, Message message, Channel channel) throws IOException {
        log.info("接收到服务[{}]消息,开始同步敏感词", appName);
        processMessageAck(appName, message, channel, s -> sensitiveWordReloader.reloadLexicon(false));
    }
}
