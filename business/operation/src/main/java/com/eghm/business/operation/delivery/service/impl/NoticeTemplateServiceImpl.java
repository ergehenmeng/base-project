package com.eghm.business.operation.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.business.operation.delivery.dto.NoticeTemplateRequest;
import com.eghm.business.operation.delivery.mapper.NoticeTemplateMapper;
import com.eghm.business.operation.delivery.entity.NoticeTemplate;
import com.eghm.business.operation.delivery.service.NoticeTemplateService;
import com.eghm.business.operation.delivery.service.DeliveryCacheService;
import com.eghm.foundation.web.utility.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@Service
@AllArgsConstructor
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final DeliveryCacheService deliveryCacheService;

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
        DataUtil.copy(request, NoticeTemplate.class, noticeTemplateMapper::updateById);
    }

    @Override
    public NoticeTemplate getTemplate(String code) {
        return deliveryCacheService.getNoticeTemplate(code);
    }

}
