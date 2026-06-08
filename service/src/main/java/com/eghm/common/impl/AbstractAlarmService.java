package com.eghm.common.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.AlarmMsg;
import com.eghm.enums.AlarmType;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * @author wyb-eghm
 * @since 2026/6/2
 */
@Slf4j
public abstract class AbstractAlarmService implements AlarmService {

    protected final JsonService jsonService;

    protected final SystemProperties systemProperties;

    protected final RateLimiterRegistry rateLimiterRegistry;

    protected AbstractAlarmService(JsonService jsonService, SystemProperties systemProperties,
                                   RateLimiterRegistry rateLimiterRegistry) {
        this.jsonService = jsonService;
        this.systemProperties = systemProperties;
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @Async
    @Override
    public void sendMsg(String content) {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(this.getAlarmType().name());
        Runnable runnable = RateLimiter.decorateRunnable(rateLimiter, () -> {
            String response = HttpUtil.post(this.createRequestUrl(), this.createTextMsg(content));
            this.logResponse(response);
        });
        try {
            runnable.run();
        } catch (RequestNotPermitted e) {
            log.warn("报警消息发送被限流, 类型: {}, 内容: {}", this.getAlarmType(), content);
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