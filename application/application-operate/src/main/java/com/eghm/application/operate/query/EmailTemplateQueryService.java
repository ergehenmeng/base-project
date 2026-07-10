package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.EmailTemplate;

/**
 * 邮件模板查询服务
 *
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateQueryService {

    Page<EmailTemplate> getByPage(PagingQuery query);
}
