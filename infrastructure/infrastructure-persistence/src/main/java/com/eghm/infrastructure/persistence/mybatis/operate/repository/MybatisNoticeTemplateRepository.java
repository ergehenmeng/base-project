package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.NoticeTemplateMapper;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.domain.operate.repository.NoticeTemplateRepository;
import com.eghm.infrastructure.persistence.mybatis.po.NoticeTemplatePO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisNoticeTemplateRepository implements NoticeTemplateRepository {

    private final NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public void update(NoticeTemplate noticeTemplate) {
        noticeTemplateMapper.updateById(DataUtil.copy(noticeTemplate, NoticeTemplatePO.class));
    }
}
