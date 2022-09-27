package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.PushTemplate;
import com.eghm.model.dto.push.PushTemplateEditRequest;
import com.eghm.model.dto.push.PushTemplateQueryRequest;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:45
 */
public interface PushTemplateService {


    Page<PushTemplate> getByPage(PushTemplateQueryRequest request);

    /**
     * 获取推送消息模板
     * @param nid nid
     * @return 模板消息
     */
    PushTemplate getTemplate(String nid);

    /**
     * 主键查询推送模板
     * @param id id
     * @return 推送消息
     */
    PushTemplate getById(Long id);

    /**
     * 编辑保存推送模板
     * @param request 前台参数
     */
    void update(PushTemplateEditRequest request);
}
