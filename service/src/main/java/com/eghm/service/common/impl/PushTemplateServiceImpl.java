package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.push.PushTemplateEditRequest;
import com.eghm.dto.operate.push.PushTemplateQueryRequest;
import com.eghm.mapper.PushTemplateMapper;
import com.eghm.model.PushTemplate;
import com.eghm.service.common.PushTemplateService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.template.PushTemplateResponse;
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

    @Override
    public Page<PushTemplateResponse> getByPage(PushTemplateQueryRequest request) {
       return pushTemplateMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void update(PushTemplateEditRequest request) {
        PushTemplate template = DataUtil.copy(request, PushTemplate.class);
        pushTemplateMapper.updateById(template);
    }
}
