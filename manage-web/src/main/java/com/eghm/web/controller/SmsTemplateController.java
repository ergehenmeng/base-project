package com.eghm.web.controller;

import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 短信模板修改
 *
 * @author 二哥很猛
 * @date 2019/8/21 18:01
 */
@RestController
@Api(tags = "短信模板管理")
public class SmsTemplateController {

    private SmsTemplateService smsTemplateService;

    @Autowired
    public void setSmsTemplateService(SmsTemplateService smsTemplateService) {
        this.smsTemplateService = smsTemplateService;
    }

    /**
     * 短信模板分页列表
     */
    @GetMapping("/sms_template/list_page")
    @ApiOperation("短信模板列表(分页)")
    public Paging<SmsTemplate> listPage(SmsTemplateQueryRequest request) {
        PageInfo<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 短信模板编辑保存
     */
    @PostMapping("/sms_template/edit")
    @Mark
    @ApiOperation("编辑短信模板")
    public RespBody<Object> edit(@Valid SmsTemplateEditRequest request) {
        smsTemplateService.updateSmsTemplate(request);
        return RespBody.success();
    }
}
