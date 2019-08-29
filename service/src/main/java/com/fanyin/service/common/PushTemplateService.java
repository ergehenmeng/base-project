package com.fanyin.service.common;

import com.fanyin.dao.model.business.PushTemplate;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:45
 */
public interface PushTemplateService {

    /**
     * 获取推送消息模板
     * @param nid nid
     * @return 模板消息
     */
    PushTemplate getTemplate(String nid);
}
