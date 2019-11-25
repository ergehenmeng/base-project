package com.fanyin.service.system.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.dao.mapper.system.SmsTemplateMapper;
import com.fanyin.dao.model.business.SmsTemplate;
import com.fanyin.model.dto.business.sms.SmsTemplateEditRequest;
import com.fanyin.model.dto.business.sms.SmsTemplateQueryRequest;
import com.fanyin.service.system.SmsTemplateService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
@Service("smsTemplateService")
@Transactional(rollbackFor = RuntimeException.class)
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.SMS_TEMPLATE,key = "#p0",unless = "#result == null")
    public String getTemplate(String nid) {
        SmsTemplate smsTemplate = smsTemplateMapper.getByNid(nid);
        return smsTemplate.getContent();
    }

    @Override
    public SmsTemplate getById(Integer id) {
        return smsTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<SmsTemplate> getByPage(SmsTemplateQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<SmsTemplate> list = smsTemplateMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void updateSmsTemplate(SmsTemplateEditRequest request) {
        SmsTemplate template = DataUtil.copy(request, SmsTemplate.class);
        smsTemplateMapper.updateByPrimaryKeySelective(template);
    }


}
