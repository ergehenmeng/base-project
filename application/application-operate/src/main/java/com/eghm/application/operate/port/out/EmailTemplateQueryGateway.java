package com.eghm.application.operate.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.EmailTemplate;

/**
 * 邮件模板查询端口
 *
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateQueryGateway {

    Page<EmailTemplate> getByPage(PagingQuery query);
}
