package com.eghm.app.manage.listener;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.web.service.AlarmService;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.foundation.core.constants.QueueConstant;
import com.eghm.platform.audit.entity.ManageLog;
import com.eghm.integration.messaging.mq.listener.AbstractListenerHandler;
import com.eghm.platform.audit.service.ManageLogService;
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
@Slf4j
@Component
public class ManageListenerHandler extends AbstractListenerHandler {

    private final ManageLogService manageLogService;

    public ManageListenerHandler(JsonService jsonService, AlarmService alarmService, CacheService cacheService, ManageLogService manageLogService) {
        super(jsonService, cacheService, alarmService);
        this.manageLogService = manageLogService;
    }

    /**
     * 管理后台操作日志
     */
    @RabbitListener(queues = QueueConstant.MANAGE_LOG_QUEUE)
    public void manageLog(ManageLog log, Message message, Channel channel) throws IOException {
        processMessageAck(log, message, channel, manageLogService::insertManageLog);
    }

}
