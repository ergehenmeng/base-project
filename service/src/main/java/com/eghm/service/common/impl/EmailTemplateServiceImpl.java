package com.eghm.service.common.impl;

import com.eghm.enums.EmailType;
import com.eghm.model.EmailTemplate;
import com.eghm.service.common.EmailTemplateService;
import com.eghm.service.cache.CacheProxyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("emailTemplateService")
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final CacheProxyService cacheProxyService;

    @Override
    public EmailTemplate getByNid(EmailType code) {
        return cacheProxyService.getEmailTemplate(code);
    }
}
