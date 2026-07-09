package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.operate.template.NoticeTemplateRequest;
import com.eghm.domain.operate.model.NoticeTemplate;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
public interface NoticeTemplateApplicationService {

    /**
     * 分页查询列表
     *
     * @param query 查询
     * @return 列表
     */
    Page<NoticeTemplate> getByPage(PagingQuery query);

    /**
     * 更新模板
     *
     * @param request request
     */
    void update(NoticeTemplateRequest request);

    /**
     * 查询站内信模板
     *
     * @param code code
     * @return template
     */
    NoticeTemplate getTemplate(String code);
}
