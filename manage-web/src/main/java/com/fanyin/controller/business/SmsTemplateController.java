package com.fanyin.controller.business;

import com.fanyin.dao.model.business.SmsTemplate;
import com.fanyin.model.dto.business.sms.SmsTemplateQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.system.SmsTemplateService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信模板修改
 * @author 二哥很猛
 * @date 2019/8/21 18:01
 */
@Controller
public class SmsTemplateController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 短信模板分页列表
     */
    @PostMapping("/business/sms_template/list_page")
    @ResponseBody
    public Paging<SmsTemplate> listPage(SmsTemplateQueryRequest request){
        PageInfo<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }
}
