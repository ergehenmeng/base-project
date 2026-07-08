package com.eghm.repository.operate;

import com.eghm.mapper.EmailTemplateMapper;
import com.eghm.operate.model.EmailTemplate;
import com.eghm.operate.repository.EmailTemplateRepository;
import com.eghm.po.EmailTemplatePO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisEmailTemplateRepository implements EmailTemplateRepository {

    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public void update(EmailTemplate emailTemplate) {
        emailTemplateMapper.updateById(DataUtil.copy(emailTemplate, EmailTemplatePO.class));
    }
}
