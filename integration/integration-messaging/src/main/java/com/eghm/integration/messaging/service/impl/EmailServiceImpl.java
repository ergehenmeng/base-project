package com.eghm.integration.messaging.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HtmlUtil;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.messaging.service.EmailService;
import com.eghm.integration.messaging.dto.VerifyEmailCode;
import com.eghm.integration.messaging.dto.SendEmail;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.exception.ParameterException;
import com.eghm.integration.messaging.handler.email.AuthCodeEmailHandler;
import com.eghm.integration.messaging.handler.email.BaseEmailHandler;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


/**
 * @author 二哥很猛
 * @since 2019/7/10 17:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    
    private JavaMailSender javaMailSender;

    private MailProperties mailProperties;

    private final CacheService cacheService;
    
    private final AlarmService alarmService;

    @Autowired(required = false)
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired(required = false)
    public void setMailProperties(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendEmail(String to, String title, String content) {
        this.sendEmail(to, HtmlUtil.unescape(title), content, false);
    }

    @Override
    public void sendEmail(String to, String title, String content, boolean isHtml, File... files) {
        if (javaMailSender == null) {
            throw new ParameterException(ErrorCode.MAIL_NOT_CONFIG);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(InternetAddress.parse(mailProperties.getProperties().get("mail.from"), false)[0]);
            helper.addTo(to);
            helper.setSubject(title);
            helper.setText(content, isHtml);
            helper.setEncodeFilenames(true);
            for (File file : files) {
                helper.addAttachment(file.getName(), file);
            }
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功 to:[{}],title:[{}],content:[{}]", to, title, content);
        } catch (Exception e) {
            log.error("发送邮件异常 to:[{}],title:[{}],content:[{}]", to, title, content, e);
            alarmService.sendMsg(String.format("发送邮件异常 to:[%s],title:[%s],content:[%s]", to, title, content) + ExceptionUtil.stacktraceToString(e));
        }
    }

    @Override
    public void sendEmail(SendEmail email) {
        BaseEmailHandler handler = SpringUtil.getBean(email.getType().getHandler(), BaseEmailHandler.class);
        handler.execute(email);
    }

    @Override
    public void verifyEmailCode(VerifyEmailCode emailCode) {
        String hashKey = emailCode.getEmailType().getValue() + "::" + emailCode.getMemberId();
        String email = cacheService.getHashValue(hashKey, AuthCodeEmailHandler.EMAIL);
        if (email == null || !email.equals(emailCode.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ADDRESS_ERROR);
        }
        String authCode = cacheService.getHashValue(hashKey, AuthCodeEmailHandler.AUTH_CODE);
        if (authCode == null || !authCode.equals(emailCode.getAuthCode())) {
            throw new BusinessException(ErrorCode.EMAIL_CODE_ERROR);
        }
        // 成功之后删除,防止恶意更新
        cacheService.delete(hashKey);
    }
}
