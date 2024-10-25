package com.eghm.web.listener;

import com.eghm.cache.CacheService;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constants.QueueConstant;
import com.eghm.model.ManageLog;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.service.sys.ManageLogService;
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
public class ManageListenerHandler extends AbstractListenerHandler {

    private final ManageLogService manageLogService;

    public ManageListenerHandler(JsonService jsonService, AlarmService alarmService, CacheService cacheService, ManageLogService manageLogService) {
        super(jsonService, cacheService, alarmService);
        this.manageLogService = manageLogService;
    }

    /**
     * 管理后台操作日志
     */
    @RabbitListener(queues = QueueConstant.MANAGE_OP_LOG_QUEUE)
    public void manageOpLog(ManageLog log, Message message, Channel channel) throws IOException {
        processMessageAck(log, message, channel, manageLogService::insertManageLog);
    }

 }
