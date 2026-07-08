package com.eghm.operate.repository;

import com.eghm.operate.model.EmailTemplate;

/**
 * 邮件模板仓储接口
 *
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateRepository {

    /**
     * 更新邮件模板
     *
     * @param emailTemplate 模板信息
     */
    void update(EmailTemplate emailTemplate);
}
