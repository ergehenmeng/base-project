package com.eghm.service.sys;

import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
public interface DingTalkService {

    /**
     * 发送钉钉消息
     * @param content 消息内容
     */
    void sendMsg(String content);

    /**
     * 发送markdown模板消息
     * @param title   标题 可以为空
     * @param params  模板参数
     * @param path    模板路径
     */
    void sendMarkdownMsg(String title, Map<String, Object> params, String path);
}
