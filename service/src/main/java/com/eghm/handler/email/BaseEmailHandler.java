package com.eghm.handler.email;

import com.eghm.common.enums.EmailType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dao.model.EmailTemplate;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.common.EmailService;
import com.eghm.service.common.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Slf4j
@Component("commonEmailHandler")
public class BaseEmailHandler {

    private EmailTemplateService emailTemplateService;

    private EmailService emailService;

    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setEmailTemplateService(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 发送邮件 (主入口)
     * @param email 邮件信息
     */
    public void execute(SendEmail email) {
        // 获取模板
        EmailTemplate template = this.getCheckedTemplate(email.getType());
        // 邮件标题
        String title = this.getTitle(template, email);
        // 邮件内容
        String content = this.getContent(template, email);
        // 发送邮件
        boolean result = emailService.sendEmail(email.getEmail(), title, content);
        // 后置处理
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
     * 邮件标题 默认不进行渲染, 子类可以重写该方法来进行动态生成标题
     */
    protected String getTitle(EmailTemplate template, SendEmail email) {
        return template.getTitle();
    }

    /**
     * 邮件内容 子类可以重写该方法
     */
    protected String getContent(EmailTemplate template, SendEmail email) {
        Map<String, Object> params = this.renderParams(template, email);
        return templateEngine.render(template.getContent(), params);
    }


    /**
     * 获取邮件模板渲染时的参数, 子类可以额外定义该参数以实现更复杂的显示效果
     * @param template 模板
     * @param email 邮件发送的参数信息
     * @return 渲染参数 k-v
     */
    protected Map<String, Object> renderParams(EmailTemplate template, SendEmail email) {
        return email.getParams();
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
