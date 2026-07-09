package com.eghm.domain.shared.service;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
public interface AlarmService {

    /**
     * 发送钉钉消息
     *
     * @param content 消息内容
     */
    void sendMsg(String content);

}
