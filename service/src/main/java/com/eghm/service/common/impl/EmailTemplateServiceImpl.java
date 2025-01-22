package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.sys.email.EmailTemplateRequest;
import com.eghm.enums.EmailType;
import com.eghm.mapper.EmailTemplateMapper;
import com.eghm.model.EmailTemplate;
import com.eghm.service.common.EmailTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Service("emailTemplateService")
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final CacheProxyService cacheProxyService;

    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public Page<EmailTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<EmailTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(EmailTemplate::getTitle, query.getQueryName()).or().like(EmailTemplate::getContent, query.getQueryName()));
        wrapper.orderByDesc(EmailTemplate::getId);
        return emailTemplateMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public void update(EmailTemplateRequest request) {
        EmailTemplate template = DataUtil.copy(request, EmailTemplate.class);
        emailTemplateMapper.updateById(template);
    }

    @Override
    public EmailTemplate getByNid(EmailType code) {
        return cacheProxyService.getEmailTemplate(code);
    }
}
