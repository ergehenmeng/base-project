package com.eghm.integration.messaging.service;

import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.enums.EmailType;
import com.eghm.foundation.web.utility.MybatisUtil;
import com.eghm.integration.messaging.entity.EmailTemplate;
import com.eghm.integration.messaging.mapper.EmailTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("emailTemplateCacheService")
@RequiredArgsConstructor
public class EmailTemplateCacheService {

    private final EmailTemplateMapper emailTemplateMapper;

    @Cacheable(cacheNames = CacheConstant.EMAIL_TEMPLATE, key = "#code.value", unless = "#result == null", cacheManager = "longCacheManager")
    public EmailTemplate getEmailTemplate(EmailType code) {
        return MybatisUtil.getOne(emailTemplateMapper, EmailTemplate::getNid, code.name());
    }
}
