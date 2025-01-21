package com.eghm.common.impl;

import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.DingTalkMsg;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@AllArgsConstructor
public class DingTalkAlarmServiceImpl implements AlarmService {

    private final JsonService jsonService;

    private final SystemProperties systemProperties;

    @Async
    @Override
    public void sendMsg(String content) {
        String response = HttpUtil.post(this.createRequestUrl(), this.createTextMsg(content));
        this.parseResponse(response);
    }

    /**
     * 创建普通消息
     *
     * @param content 消息内容
     * @return 消息 json
     */
    private String createTextMsg(String content) {
        DingTalkMsg msg = new DingTalkMsg();
        msg.setMsgType("text");
        DingTalkMsg.Text text = new DingTalkMsg.Text();
        String appName = SpringContextUtil.getApplicationContext().getEnvironment().getProperty("spring.application.name");
        String builder = "[traceId]: " + LogTraceHolder.getTraceId() + "\n" +
                "[服务名]: " + appName + "\n" +
                "[信息]: " + content;
        text.setContent(builder);
        msg.setText(text);
        return jsonService.toJson(msg);
    }

    /**
     * 组装请求url
     *
     * @return <a href="https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=xx&sign=xx">请求地址</a>
     */
    private String createRequestUrl() {
        SystemProperties.AlarmMsg alarmMsg = systemProperties.getAlarmMsg();
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("access_token", alarmMsg.getWebHook());
        if (isNotBlank(alarmMsg.getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + alarmMsg.getSecret();
            String sign = SecureUtil.hmacSha256(alarmMsg.getSecret()).digestBase64(unSign, true);
            paramMap.put("timestamp", timestamp);
            paramMap.put("sign", sign);
        }
        return "https://oapi.dingtalk.com/robot/send?" + URLUtil.buildQuery(paramMap, StandardCharsets.UTF_8);
    }

    /**
     * 打印响应日志
     *
     * @param responseBody 内容
     */
    private void parseResponse(String responseBody) {
        log.info("发送钉钉消息成功, 返回结果 [{}]", responseBody);
    }
}
