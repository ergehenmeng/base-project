package com.eghm.service.common.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.exception.ParameterException;
import com.eghm.handler.email.BaseEmailHandler;
import com.eghm.handler.email.service.BindEmailEmailHandler;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.model.dto.ext.VerifyEmailCode;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.EmailService;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;


/**
 * @author 二哥很猛
 * @date 2019/7/10 17:00
 */
@Service("emailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

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
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendEmail(SendEmail email) {
        BaseEmailHandler handler = SpringContextUtil.getBean(email.getType().getHandler(), BaseEmailHandler.class);
        handler.execute(email);
    }

    @Override
    public void verifyEmailCode(VerifyEmailCode emailCode) {
        String hashKey = emailCode.getEmailType().getValue() + "::" + emailCode.getUserId();
        String email = cacheService.getHashValue(hashKey, BindEmailEmailHandler.EMAIL);
        if (email == null || !email.equals(emailCode.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ADDRESS_ERROR);
        }
        String authCode = cacheService.getHashValue(hashKey, BindEmailEmailHandler.AUTH_CODE);
        if (authCode == null || !authCode.equals(emailCode.getAuthCode())) {
            throw new BusinessException(ErrorCode.EMAIL_CODE_ERROR);
        }
        // 成功之后删除,防止恶意更新
        cacheService.delete(hashKey);
    }
}
