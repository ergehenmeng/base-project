package com.eghm.handler.email;

import com.eghm.common.enums.EmailType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.business.EmailTemplate;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.common.EmailService;
import com.eghm.service.common.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Slf4j
public class BaseEmailHandler {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private EmailService emailService;

    /**
     * 发送邮件 (主入口)
     * @param email 邮件信息
     */
    public void handler(SendEmail email) {
        EmailTemplate template = this.getCheckedTemplate(email.getType());
        String title = this.getTitle(template, email);
        String content = this.getContent(template, email);
        boolean result = emailService.sendEmail(email.getEmail(), title, content);
        this.finallyProcess(email, template, result);
    }


    private EmailTemplate getCheckedTemplate(EmailType type) {
        EmailTemplate template = emailTemplateService.getByNid(type);
        if (template == null) {
            log.warn("邮件模板未查询到 type:[{}]", type);
            throw new BusinessException(ErrorCode.EMAIL_TEMPLATE_NULL);
        }
        return template;
    }

    /**
     * 邮件内容 子类可以重写该方法
     */
    protected String getContent(EmailTemplate template, SendEmail email) {
        return template.getContent();
    }

    /**
     * 邮件标题 子类可以重写该方法
     */
    protected String getTitle(EmailTemplate template, SendEmail email) {
        return template.getTitle();
    }

    /**
     * 邮件发送完成可额外添加处理逻辑
     * @param email 发送邮件信息
     * @param template 模板信息
     * @param result 发送结果状态 true:发送成功 false:发送失败
     */
    protected void finallyProcess(SendEmail email, EmailTemplate template, boolean result) {
    }
}
