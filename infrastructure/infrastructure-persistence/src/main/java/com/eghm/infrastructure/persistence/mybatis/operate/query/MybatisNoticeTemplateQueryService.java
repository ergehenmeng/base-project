package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.NoticeTemplateMapper;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.infrastructure.persistence.mybatis.po.NoticeTemplatePO;
import com.eghm.application.operate.query.NoticeTemplateQueryService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

@Repository
@AllArgsConstructor
public class MybatisNoticeTemplateQueryService implements NoticeTemplateQueryService {

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public Page<NoticeTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<NoticeTemplatePO> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(NoticeTemplatePO::getTitle, query.getQueryName()).or().like(NoticeTemplatePO::getContent, query.getQueryName()));
        wrapper.orderByDesc(NoticeTemplatePO::getId);
        return MybatisPageUtil.copy(noticeTemplateMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage()), wrapper), NoticeTemplate.class);
    }
}


