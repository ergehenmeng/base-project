package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.sms.SmsTemplateEditRequest;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@AllArgsConstructor
@RequestMapping("/smsTemplate")
public class SmsTemplateController {

    private final SmsTemplateService smsTemplateService;

    /**
     * 短信模板分页列表
     */
    @GetMapping("/listPage")
    @ApiOperation("短信模板列表(分页)")
    public RespBody<PageData<SmsTemplate>> listPage(SmsTemplateQueryRequest request) {
        Page<SmsTemplate> byPage = smsTemplateService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    /**
     * 短信模板编辑保存
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑短信模板")
    public RespBody<Void> edit(@Valid SmsTemplateEditRequest request) {
        smsTemplateService.updateSmsTemplate(request);
        return RespBody.success();
    }
}
