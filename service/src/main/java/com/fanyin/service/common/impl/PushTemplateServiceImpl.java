package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.dao.mapper.business.PushTemplateMapper;
import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.model.dto.business.push.PushTemplateEditRequest;
import com.fanyin.model.dto.business.push.PushTemplateQueryRequest;
import com.fanyin.service.common.PushTemplateService;
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
 * @date 2019/8/29 10:45
 */
@Service("pushTemplateService")
@Transactional(rollbackFor = RuntimeException.class,readOnly = true)
public class PushTemplateServiceImpl implements PushTemplateService {

    @Autowired
    private PushTemplateMapper pushTemplateMapper;

    @Override
    public PageInfo<PushTemplate> getByPage(PushTemplateQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<PushTemplate> list = pushTemplateMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.PUSH_TEMPLATE,key = "#p0",unless = "#result == null")
    public PushTemplate getTemplate(String nid) {
        return pushTemplateMapper.getByNid(nid);
    }

    @Override
    public PushTemplate getById(Integer id) {
        return pushTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editPushTemplate(PushTemplateEditRequest request) {
        PushTemplate template = DataUtil.copy(request, PushTemplate.class);
        pushTemplateMapper.updateByPrimaryKeySelective(template);
    }
}
