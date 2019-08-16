package com.fanyin.service.common;

/**
 * @author 二哥很猛
 * @date 2019/7/10 17:00
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param to 邮件收件人地址
     * @param title 标题
     * @param content 内容
     */
    void sendEmail(String to, String title, String content);
}
