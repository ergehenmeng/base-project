package com.eghm.operate.repository;

import com.eghm.operate.model.NoticeTemplate;

/**
 * 站内信模板仓储接口
 *
 * @author 殿小二
 * @since 2020/9/12
 */
public interface NoticeTemplateRepository {

    /**
     * 更新模板
     *
     * @param noticeTemplate 模板信息
     */
    void update(NoticeTemplate noticeTemplate);
}
