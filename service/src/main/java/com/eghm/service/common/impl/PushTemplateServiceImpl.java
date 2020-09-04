package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.PushTemplateMapper;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
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
 * @date 2019/8/29 10:45
 */
@Service("pushTemplateService")
@Transactional(rollbackFor = RuntimeException.class, readOnly = true)
public class PushTemplateServiceImpl implements PushTemplateService {

    private PushTemplateMapper pushTemplateMapper;

    @Autowired
    public void setPushTemplateMapper(PushTemplateMapper pushTemplateMapper) {
        this.pushTemplateMapper = pushTemplateMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<PushTemplate> getByPage(PushTemplateQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<PushTemplate> list = pushTemplateMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.PUSH_TEMPLATE, key = "#p0", unless = "#result == null")
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PushTemplate getTemplate(String nid) {
        return pushTemplateMapper.getByNid(nid);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
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
