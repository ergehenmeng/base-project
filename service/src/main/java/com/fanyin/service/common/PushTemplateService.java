package com.fanyin.service.common;

import com.fanyin.dao.model.business.PushTemplate;
import com.fanyin.model.dto.business.push.PushTemplateEditRequest;
import com.fanyin.model.dto.business.push.PushTemplateQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/8/29 10:45
 */
public interface PushTemplateService {


    PageInfo<PushTemplate> getByPage(PushTemplateQueryRequest request);

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
    PushTemplate getById(Integer id);

    /**
     * 编辑保存推送模板
     * @param request 前台参数
     */
    void editPushTemplate(PushTemplateEditRequest request);
}
