package com.eghm.service.sys;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
public interface AlarmService {

    /**
     * 发送钉钉文本消息
     *
     * @param content 消息内容
     */
    void sendMsg(String content);

}
