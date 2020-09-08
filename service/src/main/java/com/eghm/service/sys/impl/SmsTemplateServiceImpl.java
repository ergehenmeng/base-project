package com.eghm.service.sys.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.SmsTemplateMapper;
import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
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
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private SmsTemplateMapper smsTemplateMapper;

    @Autowired
    public void setSmsTemplateMapper(SmsTemplateMapper smsTemplateMapper) {
        this.smsTemplateMapper = smsTemplateMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SMS_TEMPLATE, key = "#p0", unless = "#result == null")
    public String getTemplate(String nid) {
        SmsTemplate smsTemplate = smsTemplateMapper.getByNid(nid);
        return smsTemplate.getContent();
    }

    @Override
    public SmsTemplate getById(Integer id) {
        return smsTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SmsTemplate> getByPage(SmsTemplateQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SmsTemplate> list = smsTemplateMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSmsTemplate(SmsTemplateEditRequest request) {
        SmsTemplate template = DataUtil.copy(request, SmsTemplate.class);
        smsTemplateMapper.updateByPrimaryKeySelective(template);
    }


}
