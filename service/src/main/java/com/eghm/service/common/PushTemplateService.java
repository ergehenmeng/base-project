package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.push.PushTemplateEditRequest;
import com.eghm.dto.operate.push.PushTemplateQueryRequest;
import com.eghm.model.PushTemplate;
import com.eghm.vo.template.PushTemplateResponse;

/**
 * @author 二哥很猛
 * @since 2019/8/29 10:45
 */
public interface PushTemplateService {

    /**
     * 分页查询推送模板
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<PushTemplateResponse> getByPage(PushTemplateQueryRequest request);

    /**
     * 获取推送消息模板
     *
     * @param nid nid
     * @return 模板消息
     */
    PushTemplate getTemplate(String nid);

    /**
     * 主键查询推送模板
     *
     * @param id id
     * @return 推送消息
     */
    PushTemplate getById(Long id);

    /**
     * 编辑保存推送模板
     *
     * @param request 前台参数
     */
    void update(PushTemplateEditRequest request);
}
