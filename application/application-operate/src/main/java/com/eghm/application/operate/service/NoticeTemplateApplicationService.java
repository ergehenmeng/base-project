package com.eghm.application.operate.service;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.operate.template.NoticeTemplateRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.domain.operate.repository.NoticeTemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@Service
@AllArgsConstructor
public class NoticeTemplateApplicationService {

    private final CacheProxyService cacheProxyService;

    private final NoticeTemplateRepository noticeTemplateRepository;

    /**
     * 更新模板
     *
     * @param request request
     */
    public void update(NoticeTemplateRequest request) {
        NoticeTemplate noticeTemplate = DataUtil.copy(request, NoticeTemplate.class);
        noticeTemplateRepository.update(noticeTemplate);
    }

    /**
     * 查询站内信模板
     *
     * @param code code
     * @return template
     */
    public NoticeTemplate getTemplate(String code) {
        return cacheProxyService.getNoticeTemplate(code);
    }
}
