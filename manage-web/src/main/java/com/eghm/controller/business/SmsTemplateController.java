package com.eghm.controller.business;

import com.eghm.dao.model.business.SmsTemplate;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.sys.SmsTemplateService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信模板修改
 *
 * @author 二哥很猛
 * @date 2019/8/21 18:01
 */
@Controller
public class SmsTemplateController {

    private SmsTemplateService smsTemplateService;

    @Autowired
    public void setSmsTemplateService(SmsTemplateService smsTemplateService) {
        this.smsTemplateService = smsTemplateService;
    }

    /**
     * 短信模板分页列表
     */
    @PostMapping("/business/sms_template/list_page")
    @ResponseBody
    public Paging<SmsTemplate> listPage(SmsTemplateQueryRequest request) {
        PageInfo<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 短信模板编辑页面
     */
    @GetMapping("/business/sms_template/edit_page")
    public String editPage(Model model, Integer id) {
        SmsTemplate template = smsTemplateService.getById(id);
        model.addAttribute("template", template);
        return "business/sms_template/edit_page";
    }

    /**
     * 短信模板编辑保存
     */
    @PostMapping("/business/sms_template/edit")
    @ResponseBody
    public RespBody<Object> edit(SmsTemplateEditRequest request) {
        smsTemplateService.updateSmsTemplate(request);
        return RespBody.success();
    }
}
