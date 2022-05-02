package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.EmailType;
import com.eghm.dao.mapper.EmailTemplateMapper;
import com.eghm.dao.model.EmailTemplate;
import com.eghm.service.common.EmailTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("emailTemplateService")
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.EMAIL_TEMPLATE, key = "#code.name()", cacheManager = "longCacheManager", unless = "#result == null")
    public EmailTemplate getByNid(EmailType code) {
        return emailTemplateMapper.getByNid(code.name());
    }
}
