package com.eghm.web.listener;

import com.eghm.cache.CacheService;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constants.QueueConstant;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.model.WebappLog;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.service.business.LoginService;
import com.eghm.service.sys.WebappLogService;
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

    public WebappListenerHandler(JsonService jsonService, AlarmService alarmService, LoginService loginService, CacheService cacheService, WebappLogService webappLogService) {
        super(jsonService, cacheService, alarmService);
        this.loginService = loginService;
        this.webappLogService = webappLogService;
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


}
