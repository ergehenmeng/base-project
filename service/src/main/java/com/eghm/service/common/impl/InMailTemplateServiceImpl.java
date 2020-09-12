package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.InMailTemplateMapper;
import com.eghm.dao.model.InMailTemplate;
import com.eghm.service.common.InMailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@Service("inMailTemplateService")
public class InMailTemplateServiceImpl implements InMailTemplateService {

    private InMailTemplateMapper inMailTemplateMapper;

    @Autowired
    public void setInMailTemplateMapper(InMailTemplateMapper inMailTemplateMapper) {
        this.inMailTemplateMapper = inMailTemplateMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.IN_MAIL_TEMPLATE, key = "#p0", cacheManager = "cacheManager", unless = "#result == null")
    public InMailTemplate getTemplate(String code) {
        return inMailTemplateMapper.getTemplate(code);
    }
}
