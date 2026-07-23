package com.eghm.integration.messaging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.integration.messaging.dto.EmailTemplateRequest;
import com.eghm.foundation.core.enums.EmailType;
import com.eghm.integration.messaging.mapper.EmailTemplateMapper;
import com.eghm.integration.messaging.entity.EmailTemplate;
import com.eghm.integration.messaging.service.EmailTemplateService;
import com.eghm.integration.messaging.service.EmailTemplateCacheService;
import com.eghm.foundation.web.utility.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@AllArgsConstructor
@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateMapper emailTemplateMapper;
    
    private final EmailTemplateCacheService emailTemplateCacheService;

    @Override
    public Page<EmailTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<EmailTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(EmailTemplate::getTitle, query.getQueryName()).or().like(EmailTemplate::getContent, query.getQueryName()));
        wrapper.orderByDesc(EmailTemplate::getId);
        return emailTemplateMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public void update(EmailTemplateRequest request) {
        DataUtil.copy(request, EmailTemplate.class, emailTemplateMapper::updateById);
    }

    @Override
    public EmailTemplate getByNid(EmailType code) {
        return emailTemplateCacheService.getEmailTemplate(code);
    }
}
