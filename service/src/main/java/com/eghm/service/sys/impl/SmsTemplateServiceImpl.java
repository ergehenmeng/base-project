package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.sms.SmsTemplateEditRequest;
import com.eghm.mapper.SmsTemplateMapper;
import com.eghm.model.SmsTemplate;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.template.SmsTemplateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/8/21 10:35
 */
@Service("smsTemplateService")
@AllArgsConstructor
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private final SmsTemplateMapper smsTemplateMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<SmsTemplateResponse> getByPage(PagingQuery request) {
        return smsTemplateMapper.getByPage(request.createPage(), request.getQueryName());
    }

    @Override
    public String getTemplate(String nid) {
        return cacheProxyService.getSmsTemplate(nid);
    }

    @Override
    public SmsTemplate getById(Long id) {
        return smsTemplateMapper.selectById(id);
    }

    @Override
    public void update(SmsTemplateEditRequest request) {
        SmsTemplate template = DataUtil.copy(request, SmsTemplate.class);
        smsTemplateMapper.updateById(template);
    }
}
