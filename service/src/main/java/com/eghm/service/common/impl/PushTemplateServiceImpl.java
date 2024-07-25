package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.push.PushTemplateEditRequest;
import com.eghm.dto.push.PushTemplateQueryRequest;
import com.eghm.mapper.PushTemplateMapper;
import com.eghm.model.PushTemplate;
import com.eghm.service.common.PushTemplateService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.push.PushTemplateResponse;
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
    public Page<PushTemplateResponse> getByPage(PushTemplateQueryRequest request) {
       return pushTemplateMapper.getByPage(request.createPage(), request);
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
