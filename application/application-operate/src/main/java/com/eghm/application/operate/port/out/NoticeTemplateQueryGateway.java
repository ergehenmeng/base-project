package com.eghm.application.operate.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.NoticeTemplate;

/**
 * 站内信模板查询端口
 *
 * @author 殿小二
 * @since 2020/9/12
 */
public interface NoticeTemplateQueryGateway {

    Page<NoticeTemplate> getByPage(PagingQuery query);
}
