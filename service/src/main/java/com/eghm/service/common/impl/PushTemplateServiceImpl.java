package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.PushTemplateMapper;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import com.eghm.service.common.PushTemplateService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:45
 */
@Service("pushTemplateService")
public class PushTemplateServiceImpl implements PushTemplateService {

    private PushTemplateMapper pushTemplateMapper;

    @Autowired
    public void setPushTemplateMapper(PushTemplateMapper pushTemplateMapper) {
        this.pushTemplateMapper = pushTemplateMapper;
    }

    @Override
    public Page<PushTemplate> getByPage(PushTemplateQueryRequest request) {
        LambdaQueryWrapper<PushTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, PushTemplate::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(PushTemplate::getTitle, request.getQueryName()).or()
                        .like(PushTemplate::getNid, request.getQueryName()).or()
                        .like(PushTemplate::getTag, request.getQueryName()));
        return pushTemplateMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.PUSH_TEMPLATE, key = "#p0", unless = "#result == null")
    public PushTemplate getTemplate(String nid) {
        return pushTemplateMapper.getByNid(nid);
    }

    @Override
    public PushTemplate getById(Long id) {
        return pushTemplateMapper.selectById(id);
    }

    @Override
    public void editPushTemplate(PushTemplateEditRequest request) {
        PushTemplate template = DataUtil.copy(request, PushTemplate.class);
        pushTemplateMapper.updateById(template);
    }
}
