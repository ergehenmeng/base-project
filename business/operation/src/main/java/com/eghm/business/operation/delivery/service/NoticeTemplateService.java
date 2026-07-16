package com.eghm.business.operation.delivery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.business.operation.delivery.dto.NoticeTemplateRequest;
import com.eghm.business.operation.delivery.entity.NoticeTemplate;

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
