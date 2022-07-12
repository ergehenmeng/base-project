package com.eghm.service.common.impl;

import com.eghm.dao.model.NoticeTemplate;
import com.eghm.service.common.NoticeTemplateService;
import com.eghm.service.cache.CacheProxyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@Service("noticeTemplateService")
@AllArgsConstructor
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final CacheProxyService cacheProxyService;

    @Override
    public NoticeTemplate getTemplate(String code) {
        return cacheProxyService.getNoticeTemplate(code);
    }
}
