package com.eghm.query.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.EmailTemplateMapper;
import com.eghm.operate.model.EmailTemplate;
import com.eghm.po.EmailTemplatePO;
import com.eghm.service.operate.EmailTemplateQueryGateway;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eghm.utils.StringUtil.isNotBlank;

@Repository
@AllArgsConstructor
public class MybatisEmailTemplateQueryGateway implements EmailTemplateQueryGateway {

    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public Page<EmailTemplate> getByPage(PagingQuery query) {
        LambdaQueryWrapper<EmailTemplatePO> wrapper = Wrappers.lambdaQuery();
        wrapper.and(isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(EmailTemplatePO::getTitle, query.getQueryName()).or().like(EmailTemplatePO::getContent, query.getQueryName()));
        wrapper.orderByDesc(EmailTemplatePO::getId);
        return MybatisPageUtil.copy(emailTemplateMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage()), wrapper), EmailTemplate.class);
    }
}


