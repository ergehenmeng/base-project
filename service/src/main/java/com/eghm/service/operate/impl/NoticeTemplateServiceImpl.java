package com.eghm.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.template.NoticeTemplateRequest;
import com.eghm.mapper.NoticeTemplateMapper;
import com.eghm.model.NoticeTemplate;
import com.eghm.service.operate.NoticeTemplateService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@AllArgsConstructor
@Service("noticeTemplateService")
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final CacheProxyService cacheProxyService;

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public Page<NoticeTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<NoticeTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(NoticeTemplate::getTitle, query.getQueryName()).or().like(NoticeTemplate::getContent, query.getQueryName()));
        wrapper.orderByDesc(NoticeTemplate::getId);
        return noticeTemplateMapper.selectPage(query.createPage(), wrapper);
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
