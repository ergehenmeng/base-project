package com.eghm.query.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.NoticeTemplateMapper;
import com.eghm.operate.model.NoticeTemplate;
import com.eghm.po.NoticeTemplatePO;
import com.eghm.service.operate.NoticeTemplateQueryGateway;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eghm.utils.StringUtil.isNotBlank;

@Repository
@AllArgsConstructor
public class MybatisNoticeTemplateQueryGateway implements NoticeTemplateQueryGateway {

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public Page<NoticeTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<NoticeTemplatePO> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(NoticeTemplatePO::getTitle, query.getQueryName()).or().like(NoticeTemplatePO::getContent, query.getQueryName()));
        wrapper.orderByDesc(NoticeTemplatePO::getId);
        return MybatisPageUtil.copy(noticeTemplateMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage()), wrapper), NoticeTemplate.class);
    }
}


