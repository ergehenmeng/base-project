package com.eghm.service.common.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.ApacheHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.eghm.configuration.PushProperties;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.ext.PushMessage;
import com.eghm.model.ext.PushNotice;
import com.eghm.model.ext.PushTemplateNotice;
import com.eghm.service.common.PushService;
import com.eghm.service.common.PushTemplateService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 全局系统消息类
 *
 * @author 二哥很猛
 * @date 2019/8/29 10:57
 */
@Slf4j(topic = "push_response")
public class PushServiceImpl implements PushService {

    /**
     * 移动端通知跳转标示符
     */
    private static final String VIEW_TAG = "&viewTag";

    private PushTemplateService pushTemplateService;

    private PushProperties pushProperties;

    private JPushClient pushClient;

    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setPushTemplateService(PushTemplateService pushTemplateService) {
        this.pushTemplateService = pushTemplateService;
    }

    @Autowired
    public void setPushProperties(PushProperties pushProperties) {
        this.pushProperties = pushProperties;
    }

    @PostConstruct
    public void init() {
        ClientConfig config = ClientConfig.getInstance();
        pushClient = new JPushClient(pushProperties.getMasterSecret(), pushProperties.getAppKey(), null, config);
        String authCode = ServiceHelper.getBasicAuthorization(pushProperties.getMasterSecret(), pushProperties.getAppKey());
        ApacheHttpClient client = new ApacheHttpClient(authCode, null, config);
        pushClient.getPushClient().setHttpClient(client);
        log.info("极光推送客户端初始化成功...");
    }

    @Override
    public void pushNotification(PushTemplateNotice templateNotice) {
        String nid = templateNotice.getPushType().getNid();
        PushTemplate template = pushTemplateService.getTemplate(nid);
        if (template == null) {
            log.warn("未查询到推送模板:[{}]", nid);
            return;
        }
        // 默认只对内容进行渲染操作
        String content = templateEngine.render(template.getContent(), templateNotice.getParams());
        PushNotice pushNotice = PushNotice.builder().alias(templateNotice.getAlias()).content(content).title(template.getTitle()).viewTag(template.getTag()).build();
        pushNotice.getExtras().putAll(templateNotice.getExtras());
        this.doPushNotification(pushNotice);
    }

    @Override
    public void pushNotification(PushNotice pushNotice) {
        // 将跳转的参数配置也添加extra附加参数中
        pushNotice.getExtras().put(VIEW_TAG, pushNotice.getViewTag());
        this.doPushNotification(pushNotice);
    }


    /**
     * 发送通知类推送
     * @param pushNotice 参数(完全体)
     */
    private void doPushNotification(PushNotice pushNotice) {
        try {
            PushResult pushResult = pushClient.sendPush(this.getPushPayload(pushNotice));
            if (log.isDebugEnabled()) {
                log.debug("推送通知异步返回:[{}]", pushResult);
            }
        } catch (APIConnectionException | APIRequestException e) {
            log.error("推送通知异常:[{}]", pushNotice, e);
        }
    }

    @Override
    public void pushMessage(PushMessage pushBuilder) {
        try {
            PushResult pushResult = pushClient.sendPush(this.getPushPayloadMessage(pushBuilder));
            if (log.isDebugEnabled()) {
                log.debug("推送消息异步响应:[{}]", pushResult);
            }
        } catch (Exception e) {
            log.error("推送消息异常:[{}]", pushBuilder, e);
        }
    }



    private PushPayload getPushPayloadMessage(PushMessage pushMessage) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(pushMessage.getAlias()))
                .setMessage(Message.newBuilder().setMsgContent(pushMessage.getContent()).addExtras(pushMessage.getExtras()).build())
                .build();
    }

    /**
     * 组装消息通知信息
     */
    private PushPayload getPushPayload(PushNotice pushNotice) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(pushNotice.getAlias()))
                .setNotification(Notification.newBuilder()
                                .addPlatformNotification(this.getAndroidNotification(pushNotice))
                                .addPlatformNotification(this.getIosNotification(pushNotice)).build())
                .build();
    }

    /**
     * 创建Android通知对象
     */
    private AndroidNotification getAndroidNotification(PushNotice pushNotice) {
        return AndroidNotification.newBuilder()
                .setAlert(pushNotice.getTitle())
                .setTitle(pushNotice.getContent())
                .addExtras(pushNotice.getExtras()).build();
    }

    /**
     * 创建Ios通知对象
     */
    private IosNotification getIosNotification(PushNotice pushNotice) {
        Map<String, String> alert = Maps.newHashMapWithExpectedSize(4);
        alert.put("title", pushNotice.getTitle());
        alert.put("body", pushNotice.getContent());
        return IosNotification.newBuilder()
                .setAlert(alert)
                .addExtras(pushNotice.getExtras()).build();
    }
}
