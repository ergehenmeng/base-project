package com.eghm.common;

import com.eghm.dto.ext.PushNotice;

/**
 * @author 二哥很猛
 * @since 2019/8/29 10:57
 */
public interface PushService {

    /**
     * 推送通知(别名推送) 直接发送
     *
     * @param pushNotice 消息相关参数
     */
    void pushNotification(PushNotice pushNotice);

}
