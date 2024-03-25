package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.sms.SmsTemplateEditRequest;
import com.eghm.mapper.SmsTemplateMapper;
import com.eghm.model.SmsTemplate;
import com.eghm.cache.CacheProxyService;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/8/21 10:35
 */
@Service("smsTemplateService")
@AllArgsConstructor
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<SmsTemplate> getByPage(PagingQuery request) {
        LambdaQueryWrapper<SmsTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SmsTemplate::getNid, request.getQueryName());
        return smsTemplateMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public String getTemplate(String nid) {
        return cacheProxyService.getSmsTemplate(nid);
    }

    @Override
    public SmsTemplate getById(Long id) {
        return smsTemplateMapper.selectById(id);
    }

    @Override
    public void update(SmsTemplateEditRequest request) {
        SmsTemplate template = DataUtil.copy(request, SmsTemplate.class);
        smsTemplateMapper.updateById(template);
    }
}
