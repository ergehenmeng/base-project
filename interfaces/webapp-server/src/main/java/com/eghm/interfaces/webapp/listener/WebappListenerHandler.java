package com.eghm.interfaces.webapp.listener;

import com.eghm.application.shared.cache.CacheService;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.constants.QueueConstant;
import com.eghm.application.shared.dto.ext.LoginRecord;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.application.member.port.in.LoginService;
import com.eghm.application.operate.port.in.SensitiveWordService;
import com.eghm.application.system.port.in.WebappLogService;
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

    private final SensitiveWordService sensitiveWordService;

    public WebappListenerHandler(JsonService jsonService, AlarmService alarmService, LoginService loginService, CacheService cacheService, WebappLogService webappLogService, SensitiveWordService sensitiveWordService) {
        super(jsonService, cacheService, alarmService);
        this.loginService = loginService;
        this.webappLogService = webappLogService;
        this.sensitiveWordService = sensitiveWordService;
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
        processMessageAck(appName, message, channel, s -> sensitiveWordService.reloadLexicon(false));
    }
}
