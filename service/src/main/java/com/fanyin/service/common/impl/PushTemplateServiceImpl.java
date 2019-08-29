package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.dao.mapper.business.PushTemplateMapper;
import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.service.common.PushTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:45
 */
@Service("pushTemplateService")
public class PushTemplateServiceImpl implements PushTemplateService {

    @Autowired
    private PushTemplateMapper pushTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.PUSH_TEMPLATE,key = "#p0",unless = "#result == null")
    public PushTemplate getTemplate(String nid) {
        return pushTemplateMapper.getByNid(nid);
    }
}
