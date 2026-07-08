package com.eghm.service.operate.impl;

import com.eghm.dto.ext.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.template.NoticeTemplateRequest;
import com.eghm.operate.model.NoticeTemplate;
import com.eghm.operate.repository.NoticeTemplateRepository;
import com.eghm.service.operate.NoticeTemplateQueryGateway;
import com.eghm.service.operate.NoticeTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@AllArgsConstructor
@Service("noticeTemplateService")
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final CacheProxyService cacheProxyService;

    private final NoticeTemplateRepository noticeTemplateRepository;

    private final NoticeTemplateQueryGateway noticeTemplateQueryGateway;

    @Override
    public Page<NoticeTemplate> getByPage(PagingQuery query) {
        return noticeTemplateQueryGateway.getByPage(query);
    }

    @Override
    public void update(NoticeTemplateRequest request) {
        NoticeTemplate noticeTemplate = DataUtil.copy(request, NoticeTemplate.class);
        noticeTemplateRepository.update(noticeTemplate);
    }

    @Override
    public NoticeTemplate getTemplate(String code) {
        return cacheProxyService.getNoticeTemplate(code);
    }
}
