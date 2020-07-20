package com.eghm.service.common.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.constants.ConfigConstant;
import com.eghm.service.common.EmailService;
import com.eghm.service.system.impl.SystemConfigApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


/**
 * @author 二哥很猛
 * @date 2019/7/10 17:00
 */
@Service("emailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    private SystemConfigApi systemConfigApi;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setSystemConfigApi(SystemConfigApi systemConfigApi) {
        this.systemConfigApi = systemConfigApi;
    }

    @Autowired(required = false)
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String title, String content) {
        if (javaMailSender == null) {
            throw new ParameterException(ErrorCode.MAIL_NOT_CONFIG);
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.addTo(to);
            helper.setSubject(title);
            helper.setText(content);
            helper.setFrom(systemConfigApi.getString(ConfigConstant.SEND_FROM));
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件异常 to:[{}],title:[{}],content:[{}]", to, title, content, e);
        }
    }
}
