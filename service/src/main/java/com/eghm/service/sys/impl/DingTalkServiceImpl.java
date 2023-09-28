package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.ext.DingTalkMsg;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Slf4j
@Service("dingTalkService")
@AllArgsConstructor
public class DingTalkServiceImpl implements DingTalkService {

    private final SystemProperties systemProperties;

    private final JsonService jsonService;

    private TemplateEngine templateEngine;

    @Async
    @Override
    public void sendMsg(String content) {
        String response = HttpUtil.post(this.createRequestUrl(), this.createTextMsg(content));
        this.parseResponse(response);
    }

    @Async
    @Override
    public void sendMarkdownMsg(String title, Map<String, Object> params, String path) {
        String response = HttpUtil.post(this.createRequestUrl(), this.createMarkdownMsg(title, params, path));
        this.parseResponse(response);
    }

    /**
     * 创建普通消息
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
     * 创建markdown格式的消息
     * @param title 文档标题
     * @param params 模板参数
     * @param path 模板路劲
     * @return 消息 json
     */
    private String createMarkdownMsg(String title, Map<String, Object> params, String path) {
        DingTalkMsg msg = new DingTalkMsg();
        msg.setMsgType("markdown");
        DingTalkMsg.Markdown markdown = new DingTalkMsg.Markdown();
        markdown.setTitle(title);
        String content = templateEngine.renderFile(path, params);
        markdown.setText(content);
        msg.setMarkdown(markdown);
        return jsonService.toJson(msg);
    }

    /**
     * 组装请求url
     * @return <a href="https://oapi.dingtalk.com/robot/send?access_token=xxx&timestamp=xx&sign=xx">请求地址</a>
     */
    private String createRequestUrl() {
        SystemProperties.DingTalk dingTalk = systemProperties.getDingTalk();
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("access_token", dingTalk.getAccessToken());
        if (StrUtil.isNotBlank(dingTalk.getSecret())) {
            long timestamp = System.currentTimeMillis();
            String unSign = timestamp + "\n" + dingTalk.getSecret();
            String sign = SecureUtil.hmacSha256(dingTalk.getSecret()).digestBase64(unSign, true);
            paramMap.put("timestamp", timestamp);
            paramMap.put("sign", sign);
        }
        return dingTalk.getUrl() + "?" + URLUtil.buildQuery(paramMap, StandardCharsets.UTF_8);
    }

    /**
     * 打印响应日志
     * @param responseBody 内容
     */
    private void parseResponse(String responseBody) {
        log.info("发送钉钉消息成功, 返回结果 [{}]", responseBody);
    }
}
