package com.eghm.service.common;

import com.eghm.common.enums.EmailType;
import com.eghm.common.enums.SmsType;
import com.eghm.handler.email.AuthCodeEmailHandler;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.model.ext.VerifyEmailCode;

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
     * @return true:发送成功
     */
    boolean sendEmail(String to, String title, String content);

    /**
     * 发送模板邮件
     * @param email 发送信息
     */
    void sendEmail(SendEmail email);

    /**
     * 校验邮箱验证码是否正确.<br>
     * 在{@link AuthCodeEmailHandler}该类中存入验证码相关信息,此处取出来校验一波
     * @param emailCode 校验邮箱验证码的参数信息
     */
    void verifyEmailCode(VerifyEmailCode emailCode);
}
