package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.push.PushTemplateEditRequest;
import com.eghm.dto.push.PushTemplateQueryRequest;
import com.eghm.mapper.PushTemplateMapper;
import com.eghm.model.PushTemplate;
import com.eghm.cache.CacheProxyService;
import com.eghm.service.common.PushTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/8/29 10:45
 */
@Service("pushTemplateService")
@AllArgsConstructor
public class PushTemplateServiceImpl implements PushTemplateService {

    private final PushTemplateMapper pushTemplateMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<PushTemplate> getByPage(PushTemplateQueryRequest request) {
        LambdaQueryWrapper<PushTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, PushTemplate::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper -> queryWrapper.like(PushTemplate::getTitle, request.getQueryName()).or().like(PushTemplate::getNid, request.getQueryName()).or().like(PushTemplate::getTag, request.getQueryName()));
        return pushTemplateMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public PushTemplate getTemplate(String nid) {
        return cacheProxyService.getPushTemplate(nid);
    }

    @Override
    public PushTemplate getById(Long id) {
        return pushTemplateMapper.selectById(id);
    }

    @Override
    public void update(PushTemplateEditRequest request) {
        PushTemplate template = DataUtil.copy(request, PushTemplate.class);
        pushTemplateMapper.updateById(template);
    }
}
