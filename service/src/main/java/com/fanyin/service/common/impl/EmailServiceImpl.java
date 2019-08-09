package com.fanyin.service.common.impl;

import com.fanyin.constants.ConfigConstant;
import com.fanyin.service.common.EmailService;
import com.fanyin.service.system.impl.SystemConfigApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


/**
 * @author 王艳兵
 * @date 2019/7/10 17:00
 */
@Service("emailService")
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String title, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.addTo(to);
            helper.setSubject(title);
            helper.setText(content);
            helper.setFrom(systemConfigApi.getString(ConfigConstant.SEND_FROM));
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            log.error("发送邮件异常 to:[{}],title:[{}],content:[{}]",to,title,content,e);
        }
    }
}
