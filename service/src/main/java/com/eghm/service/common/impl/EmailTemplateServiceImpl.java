package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.EmailType;
import com.eghm.dao.mapper.business.EmailTemplateMapper;
import com.eghm.dao.model.business.EmailTemplate;
import com.eghm.service.common.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private EmailTemplateMapper emailTemplateMapper;

    @Autowired
    public void setEmailTemplateMapper(EmailTemplateMapper emailTemplateMapper) {
        this.emailTemplateMapper = emailTemplateMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.EMAIL_TEMPLATE, key = "#code.name()", cacheManager = "longCacheManager", unless = "#result == null")
    public EmailTemplate getByNid(EmailType code) {
        return emailTemplateMapper.getByNid(code.name());
    }
}
