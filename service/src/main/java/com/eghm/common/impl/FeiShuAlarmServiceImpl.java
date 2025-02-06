package com.eghm.common.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.FeiShuMsg;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@AllArgsConstructor
public class FeiShuAlarmServiceImpl implements AlarmService {

    private final JsonService jsonService;

    private final SystemProperties systemProperties;

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
        String appName = SpringContextUtil.getApplicationContext().getEnvironment().getProperty("spring.application.name");
        String builder = "【服务名】：" + appName + "\n" +
                "【traceId】：" + MDC.get(CommonConstant.TRACE_ID) + "\n" +
                "【报警信息】：" + content;
        msg.setText(new FeiShuMsg.Text(builder));
        msg.setMsgType("text");
        if (isNotBlank(systemProperties.getAlarmMsg().getSecret())) {
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
