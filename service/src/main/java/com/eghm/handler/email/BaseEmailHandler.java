package com.eghm.handler.email;

import com.eghm.common.EmailService;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.operate.email.SendEmail;
import com.eghm.enums.EmailType;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.model.EmailTemplate;
import com.eghm.service.operate.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Slf4j
@Component("commonEmailHandler")
public class BaseEmailHandler {

    private EmailService emailService;

    private TemplateEngine templateEngine;

    private EmailTemplateService emailTemplateService;

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
     *
     * @param email 邮件信息
     */
    @Async
    public void execute(SendEmail email) {
        // 获取模板
        EmailTemplate template = this.getValidTemplate(email.getType());
        // 邮件标题
        String title = this.getTitle(template, email);
        // 邮件内容
        String content = this.getContent(template, email);
        // 发送邮件
        emailService.sendEmail(email.getTo(), title, content);
        // 后置处理
        this.finallyProcess(email, template);
    }

    private EmailTemplate getValidTemplate(EmailType type) {
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
        log.info("邮件模板 [{}] 邮件参数 [{}]", template.getNid(), email.getParams());
        return template.getTitle();
    }

    /**
     * 邮件内容 子类可以重写该方法
     */
    protected String getContent(EmailTemplate template, SendEmail email) {
        Map<String, Object> params = this.getRenderParams(email);
        return templateEngine.render(template.getContent(), params);
    }

    /**
     * 获取邮件模板渲染时的参数, 子类可以额外定义该参数以实现更复杂的显示效果
     *
     * @param email 邮件发送的参数信息
     * @return 渲染参数 k-v
     */
    protected Map<String, Object> getRenderParams(SendEmail email) {
        return email.getParams();
    }

    /**
     * 邮件发送完成可额外添加处理逻辑
     *
     * @param email    发送邮件信息
     * @param template 模板信息
     */
    protected void finallyProcess(SendEmail email, EmailTemplate template) {
        log.info("邮件发送成功 [{}] [{}]", email.getTo(), template.getNid());
    }

}
