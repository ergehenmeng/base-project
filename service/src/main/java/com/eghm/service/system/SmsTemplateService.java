package com.eghm.service.system;

import com.eghm.dao.model.business.SmsTemplate;
import com.eghm.model.dto.business.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.business.sms.SmsTemplateQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
public interface SmsTemplateService {

    /**
     * 获取短信发送模板
     * @param nid nid
     * @return 短信内容模板
     */
    String getTemplate(String nid);

    /**
     * 主键查询短信模板
     * @param id id
     * @return 短信模板
     */
    SmsTemplate getById(Integer id);

    /**
     * 根据条件查询短信模板列表
     * @param request 查询条件
     * @return 列表
     */
    PageInfo<SmsTemplate> getByPage(SmsTemplateQueryRequest request);

    /**
     * 更新短信模板
     * @param request 前台参数
     */
    void updateSmsTemplate(SmsTemplateEditRequest request);
}
