package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.email.EmailTemplateRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.EmailType;
import com.eghm.model.EmailTemplate;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateService {

    /**
     * 分页查询邮件模板
     *
     * @param query 分页信息
     * @return 列表
     */
    Page<EmailTemplate> getByPage(PagingQuery query);

    /**
     * 更新邮件模板
     *
     * @param request 模板信息
     */
    void update(EmailTemplateRequest request);

    /**
     * 根据邮件模板code获取
     *
     * @param code 模板code
     * @return 模板信息
     */
    EmailTemplate getByNid(EmailType code);
}
