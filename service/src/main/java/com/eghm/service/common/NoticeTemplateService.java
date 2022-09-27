package com.eghm.service.common;

import com.eghm.model.NoticeTemplate;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
public interface NoticeTemplateService {

    /**
     * 查询站内信模板
     * @param code code
     * @return template
     */
    NoticeTemplate getTemplate(String code);
}
