package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.email.EmailTemplateRequest;
import com.eghm.enums.EmailType;
import com.eghm.domain.operate.model.EmailTemplate;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateApplicationService {

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
