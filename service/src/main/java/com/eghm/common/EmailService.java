package com.eghm.common;

import com.eghm.dto.operate.email.SendEmail;
import com.eghm.dto.ext.VerifyEmailCode;
import com.eghm.handler.email.AuthCodeEmailHandler;

import java.io.File;

/**
 * @author 二哥很猛
 * @since 2019/7/10 17:00
 */
public interface EmailService {

    /**
     * 发送邮件
     *
     * @param to      邮件收件人地址
     * @param title   标题
     * @param content 内容
     */
    void sendEmail(String to, String title, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to 收件人
     * @param title 邮件标题
     * @param content 内容
     * @param isHtml 是否为html邮件
     * @param files 附件
     */
    void sendEmail(String to, String title, String content, boolean isHtml, File... files);

    /**
     * 发送模板邮件
     *
     * @param email 发送信息
     */
    void sendEmail(SendEmail email);

    /**
     * 校验邮箱验证码是否正确.<br>
     * 在{@link AuthCodeEmailHandler}该类中存入验证码相关信息,此处取出来校验一波
     *
     * @param emailCode 校验邮箱验证码的参数信息
     */
    void verifyEmailCode(VerifyEmailCode emailCode);
}
