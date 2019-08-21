package com.fanyin.service.system.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.dao.mapper.system.SmsTemplateMapper;
import com.fanyin.dao.model.system.SmsTemplate;
import com.fanyin.service.system.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;


    @Override
    @Cacheable(cacheNames = CacheConstant.SMS_TEMPLATE,key = "#p0")
    public String getTemplate(String nid) {
        SmsTemplate smsTemplate = smsTemplateMapper.getByNid(nid);
        return smsTemplate.getContent();
    }


}
