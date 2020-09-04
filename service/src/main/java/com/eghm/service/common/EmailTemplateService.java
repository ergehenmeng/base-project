package com.eghm.service.common;

import com.eghm.common.enums.EmailType;
import com.eghm.dao.model.EmailTemplate;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public interface EmailTemplateService {

    /**
     * 根据邮件模板code获取
     * @param code 模板code
     * @return 模板信息
     */
    EmailTemplate getByNid(EmailType code);
}
