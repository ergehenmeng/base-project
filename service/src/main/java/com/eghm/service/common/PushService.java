package com.eghm.service.common;

import com.eghm.common.enums.PushType;
import com.eghm.model.ext.PushBuilder;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:57
 */
public interface PushService {

    /**
     * 推送通知(别名推送)
     * @param pushType 推送模板类型
     * @param pushBuilder 消息相关参数
     * @param params 消息模板涉及到的参数
     */
    void pushNotification(PushType pushType,PushBuilder pushBuilder, Object... params);

    /**
     * 推送消息
     * @param pushBuilder 消息信息
     */
    void pushMessage(PushBuilder pushBuilder);
}
