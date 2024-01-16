package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sms.SmsTemplateEditRequest;
import com.eghm.dto.sms.SmsTemplateQueryRequest;
import com.eghm.model.SmsTemplate;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
public interface SmsTemplateService {

    /**
     * 根据条件查询短信模板列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SmsTemplate> getByPage(SmsTemplateQueryRequest request);

    /**
     * 获取短信发送模板
     *
     * @param nid nid
     * @return 短信内容模板
     */
    String getTemplate(String nid);

    /**
     * 主键查询短信模板
     *
     * @param id id
     * @return 短信模板
     */
    SmsTemplate getById(Long id);

    /**
     * 更新短信模板
     *
     * @param request 前台参数
     */
    void update(SmsTemplateEditRequest request);
}
