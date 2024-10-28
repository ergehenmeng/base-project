package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.SmsTemplate;
import com.eghm.vo.template.SmsTemplateResponse;

/**
 * @author 二哥很猛
 * @since 2019/8/21 10:35
 */
public interface SmsTemplateService {

    /**
     * 根据条件查询短信模板列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SmsTemplateResponse> getByPage(PagingQuery request);

    /**
     * 获取短信发送模板
     *
     * @param nid nid
     * @return 短信内容模板
     */
    String getTemplate(String nid);

    /**
     * 更新短信模板
     *
     * @param request 前台参数
     */
    void update(SmsTemplateEditRequest request);
}
