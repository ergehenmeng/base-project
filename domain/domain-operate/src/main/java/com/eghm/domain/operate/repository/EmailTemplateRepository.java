package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.EmailTemplate;

/**
 * 邮件模板仓储接口
 *
 * @author 殿小二
 * @since 2020/8/28
 */
public interface EmailTemplateRepository {

    /**
     * 根据id查询邮件模板
     *
     * @param id 主键
     * @return 模板信息
     */
    EmailTemplate findById(Long id);

    /**
     * 更新邮件模板
     *
     * @param emailTemplate 模板信息
     */
    void update(EmailTemplate emailTemplate);
}
