package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.template.NoticeTemplateRequest;
import com.eghm.model.NoticeTemplate;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
public interface NoticeTemplateService {

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
