package com.eghm.service.common;

import com.eghm.common.enums.EmailCode;

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

    /**
     * 发送邮件
     * @param to 邮件接收人地址
     * @param code 邮件模板类型
     */
    void sendEmail(String to, EmailCode code);
}
