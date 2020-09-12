package com.eghm.service.common;

import com.eghm.dao.model.InMailTemplate;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
public interface InMailTemplateService {

    /**
     * 查询站内信模板
     * @param code code
     * @return template
     */
    InMailTemplate getTemplate(String code);
}
