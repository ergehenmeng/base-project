package com.eghm.service.common;

import com.eghm.dto.ext.PushMessage;
import com.eghm.dto.ext.PushNotice;
import com.eghm.dto.ext.PushTemplateNotice;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:57
 */
public interface PushService {

    /**
     * 推送通知(别名推送) 通过模板发送(比较鸡肋)
     * 优先使用 {@link PushService#pushNotification(PushNotice)} 以便于做业务扩展
     * @param templateNotice 推送模板参数信息
     */
    void pushNotification(PushTemplateNotice templateNotice);

    /**
     * 推送通知(别名推送) 直接发送
     * @param pushNotice 消息相关参数
     */
    void pushNotification(PushNotice pushNotice);

    /**
     * 推送消息(后台触发)
     * @param pushMessage 消息信息
     */
    void pushMessage(PushMessage pushMessage);
}
