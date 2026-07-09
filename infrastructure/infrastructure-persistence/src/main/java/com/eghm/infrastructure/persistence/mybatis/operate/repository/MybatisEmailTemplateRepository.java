package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.EmailTemplateMapper;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.domain.operate.repository.EmailTemplateRepository;
import com.eghm.infrastructure.persistence.mybatis.po.EmailTemplatePO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisEmailTemplateRepository implements EmailTemplateRepository {

    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public EmailTemplate findById(Long id) {
        return DataUtil.copy(emailTemplateMapper.selectById(id), EmailTemplate.class);
    }

    @Override
    public void update(EmailTemplate emailTemplate) {
        emailTemplateMapper.updateById(DataUtil.copy(emailTemplate, EmailTemplatePO.class));
    }
}
