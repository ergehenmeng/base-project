package com.fanyin.controller.business;

import com.fanyin.dao.model.business.SmsTemplate;
import com.fanyin.model.dto.business.sms.SmsTemplateQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.system.SmsTemplateService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信模板修改
 * @author 二哥很猛
 * @date 2019/8/21 18:01
 */
@RestController
public class SmsTemplateController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 短信模板分页列表
     */
    @RequestMapping("/sms_template/list_page")
    public Paging<SmsTemplate> listPage(SmsTemplateQueryRequest request){
        PageInfo<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }
}
