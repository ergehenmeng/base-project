package com.eghm.common.impl;

import com.eghm.cache.CacheService;
import com.eghm.common.EmailService;
import com.eghm.dto.sys.email.SendEmail;
import com.eghm.dto.ext.VerifyEmailCode;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.exception.ParameterException;
import com.eghm.handler.email.AuthCodeEmailHandler;
import com.eghm.handler.email.BaseEmailHandler;
import com.eghm.utils.SpringContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


/**
 * @author 二哥很猛
 * @since 2019/7/10 17:00
 */
@Slf4j
@RequiredArgsConstructor
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    private final CacheService cacheService;

    @Autowired(required = false)
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendEmail(String to, String title, String content) {
        if (javaMailSender == null) {
            throw new ParameterException(ErrorCode.MAIL_NOT_CONFIG);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.addTo(to);
            helper.setSubject(title);
            helper.setText(content);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            log.error("发送邮件异常 to:[{}],title:[{}],content:[{}]", to, title, content, e);
        }
        return false;
    }

    @Override
    public void sendEmail(SendEmail email) {
        BaseEmailHandler handler = SpringContextUtil.getBean(email.getType().getHandler(), BaseEmailHandler.class);
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
