package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.template.NoticeTemplateRequest;
import com.eghm.mapper.NoticeTemplateMapper;
import com.eghm.model.NoticeTemplate;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.common.NoticeTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@AllArgsConstructor
@Service("noticeTemplateService")
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final CacheProxyService cacheProxyService;

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public Page<NoticeTemplate> getByPage(PagingQuery query) {
        return noticeTemplateMapper.selectPage(query.createPage(), null);
    }

    @Override
    public void update(NoticeTemplateRequest request) {
        NoticeTemplate template = DataUtil.copy(request, NoticeTemplate.class);
        noticeTemplateMapper.updateById(template);
    }

    @Override
    public NoticeTemplate getTemplate(String code) {
        return cacheProxyService.getNoticeTemplate(code);
    }

}
