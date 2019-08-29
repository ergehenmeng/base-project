package com.fanyin.service.common;

import com.fanyin.model.ext.PushBuilder;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:57
 */
public interface PushService {

    /**
     * 推送消息(别名推送)
     * @param pushBuilder 消息相关参数
     * @param params 消息模板涉及到的参数
     */
    void pushNotification(PushBuilder pushBuilder, Object... params);
}
