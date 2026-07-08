package com.eghm.service.operate.impl;

import com.eghm.dto.ext.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.email.EmailTemplateRequest;
import com.eghm.enums.EmailType;
import com.eghm.operate.model.EmailTemplate;
import com.eghm.operate.repository.EmailTemplateRepository;
import com.eghm.service.operate.EmailTemplateQueryGateway;
import com.eghm.service.operate.EmailTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@AllArgsConstructor
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final CacheProxyService cacheProxyService;

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailTemplateQueryGateway emailTemplateQueryGateway;

    @Override
    public Page<EmailTemplate> getByPage(PagingQuery query) {
        return emailTemplateQueryGateway.getByPage(query);
    }

    @Override
    public void update(EmailTemplateRequest request) {
        EmailTemplate emailTemplate = DataUtil.copy(request, EmailTemplate.class);
        emailTemplateRepository.update(emailTemplate);
    }

    @Override
    public EmailTemplate getByNid(EmailType code) {
        return cacheProxyService.getEmailTemplate(code);
    }
}
