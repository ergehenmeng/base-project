package com.fanyin.service.common.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.ApacheHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.fanyin.common.enums.PushType;
import com.fanyin.configuration.JpushProperties;
import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.model.ext.PushBuilder;
import com.fanyin.service.common.PushService;
import com.fanyin.service.common.PushTemplateService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 全局系统消息类: 短信,邮件,推送,站内信
 *
 * @author 二哥很猛
 * @date 2019/8/29 10:57
 */
@Slf4j(topic = "push_response")
public class PushServiceImpl implements PushService {

    @Autowired
    private PushTemplateService pushTemplateService;

    @Autowired
    private JpushProperties jpushProperties;

    private JPushClient jPushClient;

    /**
     * 移动端通知跳转标示符
     */
    private static final String PAGE_TAG = "pageTag";

    @Override
    public void pushNotification(PushType pushType, PushBuilder pushBuilder, Object... params) {
        PushTemplate template = pushTemplateService.getTemplate(pushType.getNid());
        if (template == null) {
            log.warn("未查询到推送模板:[{}]", pushType.getNid());
            return;
        }
        this.addTag(pushBuilder, template.getTag());
        try {
            PushResult pushResult = jPushClient.sendPush(this.getPushPayload(pushBuilder, MessageFormat.format(template.getContent(), params)));
            log.debug("推送通知异步返回:[{}]", pushResult);
        } catch (APIConnectionException | APIRequestException e) {
            log.error("推送通知异常:[{}]", pushBuilder, e);
        }
    }

    @Override
    public void pushMessage(PushBuilder pushBuilder) {
        try {
            PushResult pushResult = jPushClient.sendPush(this.getPushPayloadMessage(pushBuilder));
            log.debug("推送消息异步响应:[{}]", pushResult);
        } catch (Exception e) {
            log.error("推送消息异常:[{}]", pushBuilder, e);
        }
    }

    /**
     * 自定义通知打开view页面
     */
    private void addTag(PushBuilder pushBuilder, String tag) {
        Map<String, String> extras = pushBuilder.getExtras();
        extras.put(PAGE_TAG, tag);
    }

    @PostConstruct
    public void init() {
        ClientConfig config = ClientConfig.getInstance();
        jPushClient = new JPushClient(jpushProperties.getMasterSecret(), jpushProperties.getAppKey(), null, config);
        PushClient pushClient = jPushClient.getPushClient();
        String authCode = ServiceHelper.getBasicAuthorization(jpushProperties.getMasterSecret(), jpushProperties.getAppKey());
        ApacheHttpClient client = new ApacheHttpClient(authCode, null, config);
        pushClient.setHttpClient(client);
        log.info("极光推送客户端初始化成功...");
    }

    private PushPayload getPushPayloadMessage(PushBuilder pushBuilder) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(pushBuilder.getAlias()))
                .setMessage(Message.newBuilder().setMsgContent(pushBuilder.getAlert()).addExtras(pushBuilder.getExtras()).build()).build();
    }

    /**
     * 组装消息通知信息
     */
    private PushPayload getPushPayload(PushBuilder pushBuilder, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(pushBuilder.getAlias()))
                .setNotification(
                        Notification.newBuilder()
                                .addPlatformNotification(this.getAndroidNotification(pushBuilder, content))
                                .addPlatformNotification(this.getIosNotification(pushBuilder, content))
                                .build())
                .build();
    }

    /**
     * 创建Android通知对象
     *
     * @param content 通知内容
     */
    private AndroidNotification getAndroidNotification(PushBuilder pushBuilder, String content) {
        return AndroidNotification.newBuilder()
                .setAlert(pushBuilder.getAlert())
                .setTitle(content)
                .addExtras(pushBuilder.getExtras()).build();
    }

    /**
     * 创建Ios通知对象
     */
    private IosNotification getIosNotification(PushBuilder pushBuilder, String content) {
        Map<String, String> alert = Maps.newHashMapWithExpectedSize(4);
        alert.put("title", pushBuilder.getAlert());
        alert.put("body", content);
        return IosNotification.newBuilder()
                .setAlert(alert)
                .addExtras(pushBuilder.getExtras()).build();
    }
}
