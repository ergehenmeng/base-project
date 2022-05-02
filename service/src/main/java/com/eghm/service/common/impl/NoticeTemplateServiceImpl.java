package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.NoticeTemplateMapper;
import com.eghm.dao.model.NoticeTemplate;
import com.eghm.service.common.NoticeTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@Service("noticeTemplateService")
@AllArgsConstructor
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.IN_MAIL_TEMPLATE, key = "#p0", cacheManager = "cacheManager", unless = "#result == null")
    public NoticeTemplate getTemplate(String code) {
        return noticeTemplateMapper.getTemplate(code);
    }
}
