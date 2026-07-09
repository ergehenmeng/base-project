package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.EmailTemplateMapper;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.infrastructure.persistence.mybatis.po.EmailTemplatePO;
import com.eghm.application.operate.port.out.EmailTemplateQueryGateway;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

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


