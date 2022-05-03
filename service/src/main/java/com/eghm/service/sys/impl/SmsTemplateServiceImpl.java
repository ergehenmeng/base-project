package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.SmsTemplateMapper;
import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
@Service("smsTemplateService")
@AllArgsConstructor
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.SMS_TEMPLATE, key = "#p0", unless = "#result == null")
    public String getTemplate(String nid) {
        SmsTemplate smsTemplate = smsTemplateMapper.getByNid(nid);
        return smsTemplate.getContent();
    }

    @Override
    public SmsTemplate getById(Long id) {
        return smsTemplateMapper.selectById(id);
    }

    @Override
    public Page<SmsTemplate> getByPage(SmsTemplateQueryRequest request) {
        LambdaQueryWrapper<SmsTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SmsTemplate::getNid, request.getQueryName());
        return smsTemplateMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void updateSmsTemplate(SmsTemplateEditRequest request) {
        SmsTemplate template = DataUtil.copy(request, SmsTemplate.class);
        smsTemplateMapper.updateById(template);
    }
}
