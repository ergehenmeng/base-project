package com.eghm.common.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.dto.ext.WechatMsg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * 企业微信报警实现
 *
 * @author 二哥很猛
 * @since 2024/6/14
 */
@Slf4j
@AllArgsConstructor
public class WeChatAlarmServiceImpl implements AlarmService {

    private final JsonService jsonService;

    private final SystemProperties systemProperties;

    @Async
    @Override
    public void sendMsg(String content) {
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
        WechatMsg msg = new WechatMsg();
        msg.setMsgType("text");
        String appName = SpringUtil.getApplicationName();
        String builder = "【服务名】：" + appName + "\n" +
                "【traceId】：" + LogTraceHolder.getTraceId() + "\n" +
                "【报警信息】：" + content;
        msg.setText(new WechatMsg.Text(builder));
        return jsonService.toJson(msg);
    }

    /**
     * 打印响应日志
     *
     * @param responseBody 内容
     */
    private void parseResponse(String responseBody) {
        log.info("发送企业微信消息成功, 返回结果 [{}]", responseBody);
    }
}
