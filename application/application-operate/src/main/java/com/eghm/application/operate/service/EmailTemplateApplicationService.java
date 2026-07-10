package com.eghm.application.operate.service;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.operate.email.EmailTemplateRequest;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.domain.operate.repository.EmailTemplateRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmailTemplateApplicationService {

    private final CacheProxyService cacheProxyService;

    private final EmailTemplateRepository emailTemplateRepository;

    /**
     * 更新邮件模板
     *
     * @param request 模板信息
     */
    public void update(EmailTemplateRequest request) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(request.getId());
        if (emailTemplate == null) {
            throw new BusinessException(ErrorCode.EMAIL_TEMPLATE_NULL);
        }
        emailTemplate.change(request.getTitle(), request.getContent(), request.getRemark());
        emailTemplateRepository.update(emailTemplate);
    }

    /**
     * 根据邮件模板code获取
     *
     * @param code 模板code
     * @return 模板信息
     */
    public EmailTemplate getByNid(EmailType code) {
        return cacheProxyService.getEmailTemplate(code);
    }
}
