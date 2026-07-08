package com.eghm.repository.operate;

import com.eghm.mapper.NoticeTemplateMapper;
import com.eghm.operate.model.NoticeTemplate;
import com.eghm.operate.repository.NoticeTemplateRepository;
import com.eghm.po.NoticeTemplatePO;
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
