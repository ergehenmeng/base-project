package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.operate.template.NoticeTemplateRequest;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.domain.operate.repository.NoticeTemplateRepository;
import com.eghm.application.operate.query.NoticeTemplateQueryService;
import com.eghm.application.operate.service.NoticeTemplateApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@AllArgsConstructor
@Service("noticeTemplateService")
public class NoticeTemplateApplicationServiceImpl implements NoticeTemplateApplicationService {

    private final CacheProxyService cacheProxyService;

    private final NoticeTemplateRepository noticeTemplateRepository;

    private final NoticeTemplateQueryService noticeTemplateQueryGateway;

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
