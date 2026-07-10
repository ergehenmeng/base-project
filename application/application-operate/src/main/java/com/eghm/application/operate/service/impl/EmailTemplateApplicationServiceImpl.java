package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.operate.email.EmailTemplateRequest;
import com.eghm.enums.EmailType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.domain.operate.repository.EmailTemplateRepository;
import com.eghm.application.operate.service.EmailTemplateApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@AllArgsConstructor
@Service("emailTemplateService")
public class EmailTemplateApplicationServiceImpl implements EmailTemplateApplicationService {

    private final CacheProxyService cacheProxyService;

    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public void update(EmailTemplateRequest request) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(request.getId());
        if (emailTemplate == null) {
            throw new BusinessException(ErrorCode.EMAIL_TEMPLATE_NULL);
        }
        emailTemplate.change(request.getTitle(), request.getContent(), request.getRemark());
        emailTemplateRepository.update(emailTemplate);
    }

    @Override
    public EmailTemplate getByNid(EmailType code) {
        return cacheProxyService.getEmailTemplate(code);
    }
}
