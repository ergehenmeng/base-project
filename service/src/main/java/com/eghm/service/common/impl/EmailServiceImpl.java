package com.eghm.service.common.impl;

import com.eghm.common.enums.EmailType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.constants.ConfigConstant;
import com.eghm.handler.email.BaseEmailHandler;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.common.EmailService;
import com.eghm.service.sys.impl.SysConfigApi;
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

    private SysConfigApi sysConfigApi;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
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
            helper.setFrom(sysConfigApi.getString(ConfigConstant.SEND_FROM));
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            log.error("发送邮件异常 to:[{}],title:[{}],content:[{}]", to, title, content, e);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendEmail(String to, EmailType type) {
        BaseEmailHandler handler = SpringContextUtil.getBean(type.getHandler(), BaseEmailHandler.class);
        handler.handler(new SendEmail(to, type));
    }
}
