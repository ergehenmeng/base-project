package com.eghm.infrastructure.integration.common.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.AlarmMsg;
import com.eghm.enums.AlarmType;
import com.eghm.utils.DateUtil;
import com.eghm.utils.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author wyb-eghm
 * @since 2026/6/2
 */
@Slf4j
public abstract class AbstractAlarmService implements AlarmService {

    protected final JsonService jsonService;

    protected final ApplicationProperties applicationProperties;
    
    /**
     * 报警场景默认配置 18次/分钟 否则被平台限流
     */
    public static final int ALARM_LIMIT_FOR_PERIOD = 18;
    
    public static final Duration ALARM_REFRESH_PERIOD = Duration.ofSeconds(60);
    
    protected AbstractAlarmService(JsonService jsonService, ApplicationProperties applicationProperties) {
        this.jsonService = jsonService;
        this.applicationProperties = applicationProperties;
    }

    @Async
    @Override
    public void sendMsg(String content) {
        boolean tryAcquire = RateLimiterUtil.tryAcquire(this.getAlarmType().name(), ALARM_LIMIT_FOR_PERIOD, ALARM_REFRESH_PERIOD);
        if (tryAcquire) {
            String response = HttpUtil.post(this.createRequestUrl(), this.createTextMsg(content));
            this.logResponse(response);
        } else {
            log.warn("报警消息发送被限流, 类型: [{}], 内容: [{}]", this.getAlarmType().name(), content);
        }
    }

    protected abstract AlarmType getAlarmType();

    /**
     * 创建消息内容
     *
     * @param content 原信息
     * @return 整理好的内容
     */
    protected String createMessageContent(String content) {
        String appName = SpringUtil.getApplicationName();
        return "【服务名】：" + appName + "\n" +
                "【报警时间】：" + DateUtil.format(LocalDateTime.now()) + "\n" +
                "【traceId】：" + LogTraceHolder.getTraceId() + "\n" +
                "【报警信息】：" + content;
    }

    /**
     * 创建普通消息
     *
     * @param content 消息内容
     * @return 消息 json
     */
    public String createTextMsg(String content) {
        AlarmMsg msg = new AlarmMsg();
        msg.setMsgType("text");
        msg.setText(new AlarmMsg.Text(this.createMessageContent(content)));
        return jsonService.toJson(msg);
    }

    /**
     * 请求请求hook地址
     *
     * @return url
     */
    protected abstract String createRequestUrl();

    protected void logResponse(String responseBody) {
        log.info("报警信息发送消息成功, 返回结果 [{}]", responseBody);
    }
}
