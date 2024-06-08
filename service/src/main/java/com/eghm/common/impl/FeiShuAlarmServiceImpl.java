package com.eghm.common.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.FeiShuMsg;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@AllArgsConstructor
public class FeiShuAlarmServiceImpl implements AlarmService {

    private final SystemProperties systemProperties;

    private final JsonService jsonService;

    @Async
    @Override
    public void sendMsg(String content) {
        // webHook: https://open.feishu.cn/open-apis/bot/v2/hook/xxxxxxxxxxxxxxxxx
        String response = HttpUtil.post(systemProperties.getAlarmMsg().getWebHook(), this.createTextMsg(content));
        this.parseResponse(response);
    }

    /**
     * 创建普通消息
     *
     * @param content 消息内容
     * @return 消息 json
     */
    private String createTextMsg(String content) {
        FeiShuMsg msg = new FeiShuMsg();
        msg.setMsgType("text");
        FeiShuMsg.Text text = new FeiShuMsg.Text();
        String appName = SpringContextUtil.getApplicationContext().getEnvironment().getProperty("spring.application.name");
        String builder = "[traceId]: " + LogTraceHolder.getTraceId() + "\n" +
                "[服务名]: " + appName + "\n" +
                "[信息]: " + content;
        text.setContent(builder);
        msg.setText(text);
        if (StrUtil.isNotBlank(systemProperties.getAlarmMsg().getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + systemProperties.getAlarmMsg().getSecret();
            String sign = SecureUtil.hmacSha256(systemProperties.getAlarmMsg().getSecret()).digestBase64(unSign, true);
            msg.setTimestamp(timestamp);
            msg.setSign(sign);
        }
        return jsonService.toJson(msg);
    }

    /**
     * 打印响应日志
     *
     * @param responseBody 内容
     */
    private void parseResponse(String responseBody) {
        log.info("发送飞书消息成功, 返回结果 [{}]", responseBody);
    }
}
